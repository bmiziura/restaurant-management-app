package pl.bmiziura.app.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bmiziura.app.construction.model.MailTokenStatus;
import pl.bmiziura.app.construction.model.MailTokenType;
import pl.bmiziura.app.construction.model.entity.MailTokenEntity;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;
import pl.bmiziura.app.construction.model.repository.MailTokenRepository;
import pl.bmiziura.app.exception.impl.MailTokenNotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailTokenService {
    private final MailTokenRepository tokenRepository;

    public MailTokenEntity createToken(UserAccountEntity user, MailTokenType tokenType) {
        var entity = new MailTokenEntity();
        entity.setToken(generateToken());
        entity.setExpireTime(LocalDateTime.now().plusMinutes(15)); // todo add different minutes by tokenType
        entity.setType(tokenType);
        entity.setUser(user);

        entity = tokenRepository.save(entity);

        return entity;
    }

    public MailTokenEntity getToken(UserAccountEntity user, String token, MailTokenType type) {
        return tokenRepository.findByUserIdAndTokenNotExpired(user.getId(), token, type)
                .orElseThrow(() -> new MailTokenNotFoundException(user.getEmail(), token));
    }

    public boolean verifyToken(UserAccountEntity user, String token, MailTokenType type) {
        var mailToken = getToken(user, token, type);

        mailToken.setStatus(MailTokenStatus.USED);
        tokenRepository.save(mailToken);

        return true;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public int getActiveTokenCount(Long id, MailTokenType mailTokenType) {
        return tokenRepository.countAllByUserAndTypeAndTokenNotExpired(id, mailTokenType);
    }
}
