package pl.bmiziura.app.user.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bmiziura.app.infrastructure.config.security.annotation.CurrentUser;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.domain.service.UserAuthService;
import pl.bmiziura.app.user.domain.service.UserService;
import pl.bmiziura.app.user.endpoint.mapper.AuthResponseMapper;
import pl.bmiziura.app.user.endpoint.model.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "auth", description = "Authentication System")
public class AuthController {

    private final UserAuthService authService;
    private final UserService userService;
    private final AuthResponseMapper responseMapper;

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @Operation(summary = "Create a new user")
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @Operation(summary = "Get user information")
    @GetMapping("/me")
    public ResponseEntity<AuthUserResponse> getUser(@CurrentUser UserAccount user) {
        return ResponseEntity.ok(responseMapper.toUserResponse(userService.getUser(user.getId())));
    }


}
