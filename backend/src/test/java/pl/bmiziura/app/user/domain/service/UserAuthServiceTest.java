package pl.bmiziura.app.user.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bmiziura.app.infrastructure.config.security.providers.CookieProvider;
import pl.bmiziura.app.mail.domain.service.MailTokenService;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.endpoint.mapper.AuthRequestMapper;
import pl.bmiziura.app.user.endpoint.mapper.AuthResponseMapper;
import pl.bmiziura.app.user.endpoint.model.UserLoginRequest;
import pl.bmiziura.app.user.endpoint.model.UserRegisterRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {
    private static final String TEST_EMAIL = "email@example.com";
    private static final String TEST_PASSWORD = "password";

    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MailTokenService mailTokenService;
    @Mock
    private AuthRequestMapper authRequestMapper;
    @Mock
    private CookieProvider cookieProvider;

    private UserAuthService underTest;

    @BeforeEach
    void setUp() {
        underTest = spy(new UserAuthService(
                userService,
                passwordEncoder,
                mailTokenService,
                authRequestMapper,
                cookieProvider
        ));
    }

    @Test
    void shouldLoginUser() {
        // given
        var request = new UserLoginRequest(TEST_EMAIL, TEST_PASSWORD);
        var user = prepareUserAccount();
        var cookie = ResponseCookie.from("refreshToken").build();

        when(userService.getUser(request.getEmail()))
                .thenReturn(user);

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(cookieProvider.createRefreshCookie(user))
                .thenReturn(cookie);

        // when
        var result = assertDoesNotThrow(() -> underTest.loginUser(request));

        // then
        verify(userService).getUser(request.getEmail());
        verify(passwordEncoder).matches(request.getPassword(), user.getPassword());
        verify(cookieProvider).createRefreshCookie(user);

        assertThat(result).isEqualTo(cookie);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenLoggingInWithNotMatchingPasswords() {
        // given
        var request = new UserLoginRequest(TEST_EMAIL, "wrong password");
        var user = prepareUserAccount();

        when(userService.getUser(request.getEmail()))
                .thenReturn(user);

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(false);

        // when
        assertThrows(RuntimeException.class,
                () -> underTest.loginUser(request),
                "User not found!");

        // then
        verify(cookieProvider, never()).createRefreshCookie(user);
    }

    @Test
    void shouldRegisterUser() {
        // given
        var request = new UserRegisterRequest(TEST_EMAIL, TEST_PASSWORD);
        var user = prepareUserAccount();
        var cookie = ResponseCookie.from("refreshToken").build();
        var loginRequest = new UserLoginRequest(request.getEmail(), request.getPassword());

        when(authRequestMapper.toUserLoginRequest(request))
                .thenReturn(loginRequest);

        when(userService.getUser(request.getEmail()))
                .thenReturn(user);

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(cookieProvider.createRefreshCookie(user))
                .thenReturn(cookie);

        // when
        var result = assertDoesNotThrow(() -> underTest.registerUser(request));

        // then
        verify(userService).createUser(request.getEmail(), request.getPassword());
        verify(authRequestMapper).toUserLoginRequest(request);
        verify(underTest).loginUser(loginRequest);

        assertThat(result).isEqualTo(cookie);
    }

    private UserAccount prepareUserAccount() {
        return UserAccount.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }
}