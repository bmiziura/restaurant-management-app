package pl.bmiziura.app.infrastructure.config.security.providers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import pl.bmiziura.app.infrastructure.config.security.properties.TokenProperties;
import pl.bmiziura.app.user.domain.model.UserAccount;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class CookieProvider {
    private static final String REFRESH_TOKEN_NAME = "__refreshToken";
    private static final String ACCESS_TOKEN_NAME = "__accessToken";
    private final TokenProperties tokenProperties;

    @Qualifier("refreshTokenProvider")
    private final JwtTokenProvider refreshTokenProvider;

    @Qualifier("accessTokenProvider")
    private final JwtTokenProvider accessTokenProvider;

    public ResponseCookie createRefreshCookie(UserAccount userAccount) {
        var token = userAccount == null ? null : refreshTokenProvider.createToken(userAccount);

        return ResponseCookie.from(REFRESH_TOKEN_NAME, token)
                .maxAge(userAccount == null ? 0 : tokenProperties.getRefreshValidityTime())
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
        var token = userAccount == null ? null : accessTokenProvider.createToken(userAccount);

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
