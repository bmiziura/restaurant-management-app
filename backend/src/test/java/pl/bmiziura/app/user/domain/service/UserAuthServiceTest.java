package pl.bmiziura.app.user.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bmiziura.app.infrastructure.config.security.JwtTokenProvider;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.endpoint.mapper.AuthRequestMapper;
import pl.bmiziura.app.user.endpoint.mapper.AuthResponseMapper;
import pl.bmiziura.app.user.endpoint.model.UserLoginRequest;
import pl.bmiziura.app.user.endpoint.model.UserLoginResponse;
import pl.bmiziura.app.user.endpoint.model.UserRegisterRequest;
import pl.bmiziura.app.user.endpoint.model.UserRegisterResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthResponseMapper authResponseMapper;
    @Mock
    private AuthRequestMapper authRequestMapper;

    private UserAuthService underTest;

    @BeforeEach
    void setUp() {
        underTest = spy(new UserAuthService(
                userService,
                jwtTokenProvider,
                passwordEncoder,
                authResponseMapper,
                authRequestMapper
        ));
    }

    @Test
    void shouldLoginUser() {
        // given
        var request = new UserLoginRequest(TEST_EMAIL, TEST_PASSWORD);
        var user = prepareUserAccount();
        var jwtToken = "token";

        when(userService.getUser(request.getEmail()))
                .thenReturn(user);

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(jwtTokenProvider.createToken(user))
                .thenReturn(jwtToken);

        // when
        var result = assertDoesNotThrow(() -> underTest.loginUser(request));

        // then
        verify(userService).getUser(request.getEmail());
        verify(passwordEncoder).matches(request.getPassword(), user.getPassword());
        verify(jwtTokenProvider).createToken(user);

        assertThat(result).isNotNull();
        assertThat(result.getJwtToken()).isEqualTo(jwtToken);
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
        verify(jwtTokenProvider, never()).createToken(user);
    }

    @Test
    void shouldRegisterUser() {
        // given
        var request = new UserRegisterRequest(TEST_EMAIL, TEST_PASSWORD);
        var user = prepareUserAccount();
        var jwtToken = "token";
        var loginRequest = new UserLoginRequest(request.getEmail(), request.getPassword());
        var response = new UserRegisterResponse(jwtToken);

        when(authRequestMapper.toUserLoginRequest(request))
                .thenReturn(loginRequest);

        when(authResponseMapper.toUserRegisterResponse(any(UserLoginResponse.class)))
                .thenReturn(response);

        when(userService.getUser(request.getEmail()))
                .thenReturn(user);

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(jwtTokenProvider.createToken(user))
                .thenReturn(jwtToken);

        // when
        var result = assertDoesNotThrow(() -> underTest.registerUser(request));

        // then
        verify(userService).createUser(request.getEmail(), request.getPassword());
        verify(authRequestMapper).toUserLoginRequest(request);
        verify(authResponseMapper).toUserRegisterResponse(any(UserLoginResponse.class));
        verify(underTest).loginUser(loginRequest);

        assertThat(result).isNotNull();
        assertThat(result.getJwtToken()).isEqualTo(jwtToken);
    }

    private UserAccount prepareUserAccount() {
        return UserAccount.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }
}