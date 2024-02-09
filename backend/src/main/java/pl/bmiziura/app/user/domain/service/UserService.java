package pl.bmiziura.app.user.domain.service;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bmiziura.app.construction.model.MailTokenType;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;
import pl.bmiziura.app.construction.model.repository.UserAccountRepository;
import pl.bmiziura.app.exception.impl.RegisterEmailTakenException;
import pl.bmiziura.app.exception.impl.UserNotFoundException;
import pl.bmiziura.app.mail.domain.model.AccountConfirmMail;
import pl.bmiziura.app.mail.domain.service.MailService;
import pl.bmiziura.app.user.domain.mapper.UserAccountMapper;
import pl.bmiziura.app.user.domain.model.User;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.domain.model.UserRole;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private final MailTokenService mailTokenService;

    @Value("classpath:templates/assets/logo.png")
    private Resource logoFile;

    public UserAccount getUser(long id) {
        return userAccountMapper.toUserAccount(getAccountEntity(id));
    }

    public UserAccount getUser(String email) {
        return userAccountMapper.toUserAccount(getAccountEntity(email));
    }

    public UserAccountEntity getAccountEntity(long id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserAccountEntity getAccountEntity(String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public boolean userExists(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    public void createUser(String email, String password) {
        if (userExists(email)) {
            throw new RegisterEmailTakenException(email);
        }

        var user = new UserAccountEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of(UserRole.USER));

        user = userAccountRepository.save(user);

        sendActivateToken(user);
    }

    public void sendActivateToken(UserAccountEntity user) {
        System.out.println(user);
        if(user == null) return;
        if(user.isActivated()) return;

        // var token = mailTokenService.createToken(user, MailTokenType.ACCOUNT_CONFIRMATION);
        // System.out.println(token);

        try {
            System.out.println("sending mail");
            // mailService.sendMail(new AccountConfirmMail(getUser(user.getId()), token, logoFile));
            System.out.println("mail sended");
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void sendActivateToken(String email) {
        System.out.println("asdasdasd");
        UserAccountEntity entity = null;
        try {
            entity = getAccountEntity(email);
        } catch(UserNotFoundException ex) {

        }
        System.out.println("sending activate token "+entity);

        sendActivateToken(entity);
    }

    public void activateAccount(Long id) {
        var user = getAccountEntity(id);
        user.setActivated(true);

        userAccountRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = getUser(email);

        return new User(user); // todo add try catch after adding custom exception
    }
}
