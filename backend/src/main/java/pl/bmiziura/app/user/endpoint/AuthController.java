package pl.bmiziura.app.user.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bmiziura.app.infrastructure.config.security.annotation.CurrentUser;
import pl.bmiziura.app.infrastructure.config.security.providers.CookieProvider;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.domain.service.UserAuthService;
import pl.bmiziura.app.user.domain.service.UserService;
import pl.bmiziura.app.user.endpoint.mapper.AuthResponseMapper;
import pl.bmiziura.app.user.endpoint.model.AuthUserResponse;
import pl.bmiziura.app.user.endpoint.model.UserLoginRequest;
import pl.bmiziura.app.user.endpoint.model.UserRegisterRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "auth", description = "Authentication System")
public class AuthController {

    private final UserAuthService authService;
    private final UserService userService;
    private final AuthResponseMapper responseMapper;
    private final CookieProvider cookieProvider;

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody UserLoginRequest request) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, authService.loginUser(request).toString())
                .header(HttpHeaders.SET_COOKIE, cookieProvider.createAccessCookie(null).toString())
                .build();
    }

    @Operation(summary = "Create a new user")
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegisterRequest request) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, authService.registerUser(request).toString())
                .header(HttpHeaders.SET_COOKIE, cookieProvider.createAccessCookie(null).toString())
                .build();
    }

    @Operation(summary = "Activate a user using token from email")
    @PostMapping("/token/activate")
    public ResponseEntity<Void> activateUser(@RequestParam String email, @RequestParam String token) {
        authService.activateUser(email, token);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Sending another activation token to email")
    @PostMapping("/token/retry")
    public ResponseEntity<Void> resendActivationToken(@CurrentUser UserAccount user) {
        authService.sendActivateToken(user);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user information")
    @GetMapping("/me")
    public ResponseEntity<AuthUserResponse> getUser(@CurrentUser UserAccount user) {
        return ResponseEntity.ok(responseMapper.toUserResponse(userService.getUser(user.getId())));
    }

    @Operation(summary = "Logout user (delete session)")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookieProvider.createRefreshCookie(null).toString())
                .header(HttpHeaders.SET_COOKIE, cookieProvider.createAccessCookie(null).toString())
                .build();
    }


}