package pl.bmiziura.app.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bmiziura.app.exception.impl.InvalidPasswordException;
import pl.bmiziura.app.infrastructure.config.security.providers.CookieProvider;
import pl.bmiziura.app.user.endpoint.mapper.AuthRequestMapper;
import pl.bmiziura.app.user.endpoint.mapper.AuthResponseMapper;
import pl.bmiziura.app.user.endpoint.model.UserLoginRequest;
import pl.bmiziura.app.user.endpoint.model.UserRegisterRequest;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final AuthResponseMapper authResponseMapper;
    private final AuthRequestMapper authRequestMapper;

    private final CookieProvider cookieProvider;

    public ResponseCookie loginUser(UserLoginRequest request) {
        var user = userService.getUser(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException(request.getEmail());
        }

        return cookieProvider.createRefreshCookie(user);
    }

    public ResponseCookie registerUser(UserRegisterRequest request) {
        userService.createUser(request.getEmail(), request.getPassword());

        return loginUser(authRequestMapper.toUserLoginRequest(request));
    }
}
