package pl.bmiziura.app.infrastructure.config.security.providers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import pl.bmiziura.app.infrastructure.config.security.properties.TokenProperties;
import pl.bmiziura.app.infrastructure.config.security.utils.CipherUtils;
import pl.bmiziura.app.user.domain.model.RefreshToken;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.domain.service.RefreshTokenService;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class CookieProvider {
    private static final String REFRESH_TOKEN_NAME = "__refreshToken";
    private static final String ACCESS_TOKEN_NAME = "__accessToken";

    private final TokenProperties tokenProperties;

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final CipherUtils cipherUtils;

    public ResponseCookie createRefreshCookie(UserAccount userAccount) {
        RefreshToken refreshToken = userAccount == null ? null : refreshTokenService.createToken(userAccount);

        String token = refreshToken == null ? null : cipherUtils.encrypt(refreshToken.getToken());

        return ResponseCookie.from(REFRESH_TOKEN_NAME, token)
                .maxAge(userAccount == null ? 0 : tokenProperties.getRefreshValidityTime())
                .secure(true)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie updateRefreshCookie(RefreshToken refreshToken) {
        String token = refreshToken == null ? null : cipherUtils.encrypt(refreshToken.getToken());

        return ResponseCookie.from(REFRESH_TOKEN_NAME, token)
                .maxAge(refreshToken == null ? 0 : tokenProperties.getRefreshValidityTime())
                .secure(true)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();
    }

    public Optional<Cookie> getRefreshCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();

        return Stream.of(request.getCookies())
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_NAME))
                .findFirst();
    }

    public ResponseCookie createAccessCookie(UserAccount userAccount) {
        var token = userAccount == null ? null : tokenProvider.createToken(userAccount);

        return ResponseCookie.from(ACCESS_TOKEN_NAME, token)
                .maxAge(userAccount == null ? 0 : tokenProperties.getAccessValidityTime())
                .secure(true)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build();
    }

    public Optional<Cookie> getAccessCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return Optional.empty();

        return Stream.of(request.getCookies())
                .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN_NAME))
                .findFirst();
    }
}
