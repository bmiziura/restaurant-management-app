package pl.bmiziura.app.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bmiziura.app.infrastructure.config.security.JwtTokenProvider;
import pl.bmiziura.app.user.endpoint.mapper.AuthRequestMapper;
import pl.bmiziura.app.user.endpoint.mapper.AuthResponseMapper;
import pl.bmiziura.app.user.endpoint.model.UserLoginRequest;
import pl.bmiziura.app.user.endpoint.model.UserLoginResponse;
import pl.bmiziura.app.user.endpoint.model.UserRegisterRequest;
import pl.bmiziura.app.user.endpoint.model.UserRegisterResponse;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final AuthResponseMapper authResponseMapper;
    private final AuthRequestMapper authRequestMapper;

    public UserLoginResponse loginUser(UserLoginRequest request) {
        var user = userService.getUser(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("User not found!"); //todo change to custom exception after adding an exception handler
        }

        return new UserLoginResponse(jwtTokenProvider.createToken(user));
    }

    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        userService.createUser(request.getEmail(), request.getPassword());

        var loginResponse = loginUser(authRequestMapper.toUserLoginRequest(request));

        return authResponseMapper.toUserRegisterResponse(loginResponse);
    }
}
