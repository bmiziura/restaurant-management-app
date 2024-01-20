package pl.bmiziura.app.user.domain.service;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;
import pl.bmiziura.app.construction.model.repository.UserAccountRepository;
import pl.bmiziura.app.mail.domain.model.AccountConfirmMail;
import pl.bmiziura.app.mail.domain.model.Mail;
import pl.bmiziura.app.mail.domain.service.MailService;
import pl.bmiziura.app.user.domain.mapper.UserAccountMapper;
import pl.bmiziura.app.user.domain.model.User;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.domain.model.UserRole;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final Long USER_ID = 0L;
    private static final String USER_EMAIL = "email@example.com";
    private static final String USER_PASSWORD = "password";

    @Mock
    private UserAccountRepository userAccountRepository;
    @Mock
    private UserAccountMapper userAccountMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MailService mailService;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = spy(new UserService(
                userAccountRepository,
                userAccountMapper,
                passwordEncoder,
                mailService
        ));
    }

    @Test
    void shouldReturnUserById() {
        // given
        var entity = prepareEntity();
        var user = prepareUser();

        when(userAccountRepository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        when(userAccountMapper.toUserAccount(entity))
                .thenReturn(user);

        // when
        var result = assertDoesNotThrow(() -> underTest.getUser(user.getId()));

        // then
        verify(underTest).getAccountEntity(user.getId());
        verify(userAccountMapper).toUserAccount(entity);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldReturnUserByEmail() {
        // given
        var entity = prepareEntity();
        var user = prepareUser();

        when(userAccountRepository.findByEmail(entity.getEmail()))
                .thenReturn(Optional.of(entity));

        when(userAccountMapper.toUserAccount(entity))
                .thenReturn(user);

        // when
        var result = assertDoesNotThrow(() -> underTest.getUser(user.getEmail()));

        // then
        verify(underTest).getAccountEntity(user.getEmail());
        verify(userAccountMapper).toUserAccount(entity);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldReturnAccountEntityById() {
        // given
        var entity = prepareEntity();

        when(userAccountRepository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        // when
        var result = assertDoesNotThrow(() -> underTest.getAccountEntity(entity.getId()));

        // then
        verify(userAccountRepository).findById(entity.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenGettingAccountEntityWithInvalidId() {
        // given
        when(userAccountRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class,
                () -> underTest.getAccountEntity(USER_ID),
                "User not found!");
    }

    @Test
    void shouldReturnAccountEntityByEmail() {
        // given
        var entity = prepareEntity();

        when(userAccountRepository.findByEmail(entity.getEmail()))
                .thenReturn(Optional.of(entity));

        // when
        var result = assertDoesNotThrow(() -> underTest.getAccountEntity(entity.getEmail()));

        // then
        verify(userAccountRepository).findByEmail(entity.getEmail());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenGettingAccountEntityWithInvalidEmail() {
        // given
        when(userAccountRepository.findByEmail(USER_EMAIL))
                .thenReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class,
                () -> underTest.getAccountEntity(USER_EMAIL),
                "User not found!");
    }

    @Test
    void shouldCheckIfUserExists() {
        // given
        when(userAccountRepository.existsByEmail(USER_EMAIL))
                .thenReturn(true);

        // when
        var result = assertDoesNotThrow(() -> underTest.userExists(USER_EMAIL));

        // then
        verify(userAccountRepository).existsByEmail(USER_EMAIL);

        assertThat(result).isTrue();
    }

    @Test
    void shouldCreateUser() throws MessagingException, TemplateException, IOException {
        // given
        var entity = prepareEntity();
        var encodedPassword = "encodedPassword";

        when(userAccountRepository.existsByEmail(USER_EMAIL))
                .thenReturn(false);

        when(passwordEncoder.encode(USER_PASSWORD))
                .thenReturn(encodedPassword);

        when(userAccountRepository.save(any(UserAccountEntity.class)))
                .thenReturn(entity);

        when(userAccountRepository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        when(userAccountMapper.toUserAccount(entity))
                .thenReturn(prepareUser());

        // when
        assertDoesNotThrow(() -> underTest.createUser(USER_EMAIL, USER_PASSWORD));

        // then
        verify(underTest).userExists(USER_EMAIL);
        verify(passwordEncoder).encode(USER_PASSWORD);

        ArgumentCaptor<UserAccountEntity> userArgument = ArgumentCaptor.forClass(UserAccountEntity.class);
        verify(userAccountRepository).save(userArgument.capture());

        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(mailService).sendMail(mailCaptor.capture());

        var user = userArgument.getValue();
        var mail = mailCaptor.getValue();
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        assertThat(user.getPassword()).isEqualTo(encodedPassword);
        assertThat(user.getRoles().size()).isEqualTo(1);
        assertThat(user.getRoles().contains(UserRole.USER)).isTrue();
        assertThat(mail).isInstanceOf(AccountConfirmMail.class);
        assertThat(mail.getRecipient()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenCreatingUserWithAlreadyExistingEmail() {
        // given
        when(userAccountRepository.existsByEmail(USER_EMAIL))
                .thenReturn(true);

        // when
        assertThrows(RuntimeException.class,
                () -> underTest.createUser(USER_EMAIL, USER_PASSWORD),
                "Unable to create a new account! This email is already taken!");

        // then
        verify(underTest).userExists(USER_EMAIL);

        verify(userAccountRepository, never()).save(any(UserAccountEntity.class));
    }

    @Test
    void shouldLoadUserByUsername() {
        // given
        var entity = prepareEntity();
        var user = prepareUser();

        var role = new SimpleGrantedAuthority(UserRole.USER.toString());

        when(userAccountRepository.findByEmail(entity.getEmail()))
                .thenReturn(Optional.of(entity));

        when(userAccountMapper.toUserAccount(entity))
                .thenReturn(user);

        // when
        var result = (User) assertDoesNotThrow(() -> underTest.loadUserByUsername(user.getEmail()));

        // then
        verify(underTest).getUser(user.getEmail());

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(user.getEmail());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getAuthorities().size()).isEqualTo(1);
        assertThat(result.getAuthorities()).isEqualTo(Set.of(role));
        assertThat(result.getUser()).isEqualTo(user);
    }

    private UserAccountEntity prepareEntity() {
        var entity = new UserAccountEntity();
        entity.setId(USER_ID);
        entity.setEmail(USER_EMAIL);
        entity.setPassword(USER_PASSWORD);
        entity.setRoles(Set.of(UserRole.USER));

        return entity;
    }

    private UserAccount prepareUser() {
        var user = new UserAccount();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        user.setRoles(Set.of(UserRole.USER));

        return user;
    }
}