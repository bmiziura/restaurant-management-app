package pl.bmiziura.app.mail.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.bmiziura.app.construction.model.MailTokenStatus;
import pl.bmiziura.app.construction.model.MailTokenType;
import pl.bmiziura.app.construction.model.entity.MailTokenEntity;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;
import pl.bmiziura.app.construction.model.repository.MailTokenRepository;
import pl.bmiziura.app.exception.impl.*;
import pl.bmiziura.app.mail.domain.mapper.MailTokenMapper;
import pl.bmiziura.app.mail.domain.model.AccountConfirmMail;
import pl.bmiziura.app.mail.domain.model.AccountRecoveryMail;
import pl.bmiziura.app.mail.domain.model.Mail;
import pl.bmiziura.app.mail.domain.model.MailToken;
import pl.bmiziura.app.user.domain.mapper.UserAccountMapper;
import pl.bmiziura.app.user.domain.service.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailTokenService {
    private final UserService userService;
    private final MailTokenRepository tokenRepository;
    private final MailService mailService;

    private final UserAccountMapper userAccountMapper;
    private final MailTokenMapper mailTokenMapper;

    @Value("classpath:templates/assets/logo.png")
    private Resource logoFile;

    public MailToken createToken(String email, MailTokenType type) {
        var user = userService.getAccountEntity(email);

        if(type == MailTokenType.ACCOUNT_CONFIRMATION && user.isActivated()) {
            throw new UserAccountAlreadyActivatedException(email);
        }

        validateTokenLimit(user, type);

        var entity = new MailTokenEntity();
        entity.setToken(generateToken());
        entity.setExpireTime(LocalDateTime.now().plusMinutes(15)); // todo add different minutes by tokenType
        entity.setType(type);
        entity.setUser(user);

        entity = tokenRepository.save(entity);

        try {
            mailService.sendMail(createEmailMessage(user, entity));
        } catch (Exception e) {
            throw new MailSenderException(email, e);
        }

        return mailTokenMapper.toMailToken(entity);
    }

    public MailToken getToken(String email, String token, MailTokenType type) {
        try {
            var user = userService.getAccountEntity(email);

            return mailTokenMapper.toMailToken(getTokenEntity(user, token, type));
        } catch (UserNotFoundException ex) {
            throw new MailTokenNotFoundException(email, token);
        }
    }

    public boolean useToken(String email, String token, MailTokenType type) {
        try {
            var user = userService.getAccountEntity(email);

            if(type == MailTokenType.ACCOUNT_CONFIRMATION && user.isActivated()) {
                throw new UserAccountAlreadyActivatedException(email);
            }

            var entity= getTokenEntity(user, token, type);
            entity.setStatus(MailTokenStatus.USED);
            tokenRepository.save(entity);
        } catch (UserNotFoundException ex) {
            throw new MailTokenNotFoundException(email, token);
        }

        return true;
    }

    public MailTokenEntity getTokenEntity(UserAccountEntity user, String token, MailTokenType type) {
        return tokenRepository.findByUserIdAndTokenNotExpired(user.getId(), token, type)
                .orElseThrow(() -> new MailTokenNotFoundException(user.getEmail(), token));
    }

    private Mail createEmailMessage(UserAccountEntity user, MailTokenEntity token) {
        var userAccount = userAccountMapper.toUserAccount(user);

        return switch(token.getType()) {
            case ACCOUNT_CONFIRMATION -> new AccountConfirmMail(userAccount, token, logoFile);
            case PASSWORD_CHANGE -> new AccountRecoveryMail(userAccount, token, logoFile);
            default -> throw new MailSenderException("Email Message with type ("+token.getType()+") not found!");
        };
    }

    private void validateTokenLimit(UserAccountEntity user, MailTokenType type) {
        var limit = type.getLimit();
        if(getActiveTokenCount(user.getId(), type) >= limit) {
            throw new MailTokenLimitException(user.getId(), limit);
        }
    }

    public int getActiveTokenCount(Long id, MailTokenType type) {
        return tokenRepository.countAllByUserAndTypeAndTokenNotExpired(id, type);
    }
    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
