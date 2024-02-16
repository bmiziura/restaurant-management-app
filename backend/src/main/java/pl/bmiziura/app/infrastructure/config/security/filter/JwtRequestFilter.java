package pl.bmiziura.app.infrastructure.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.bmiziura.app.exception.impl.RefreshTokenNotFoundException;
import pl.bmiziura.app.infrastructure.config.security.providers.CookieProvider;
import pl.bmiziura.app.infrastructure.config.security.providers.JwtTokenProvider;
import pl.bmiziura.app.infrastructure.config.security.utils.CipherUtils;
import pl.bmiziura.app.user.domain.service.RefreshTokenService;
import pl.bmiziura.app.user.domain.service.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final CookieProvider cookieProvider;

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    private final CipherUtils cipherUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var refreshCookie = cookieProvider.getRefreshCookie(request);
        var accessCookie = cookieProvider.getAccessCookie(request);

        try {
            if (refreshCookie.isEmpty())
                throw new JwtAuthenticationException("Request does not contain Refresh Cookie!");

            var accessToken = accessCookie.map(Cookie::getValue).orElse(null);
            if (!StringUtils.hasText(accessToken) || !tokenProvider.validateToken(accessToken)) {
                var refreshTokenValue = cipherUtils.decrypt(refreshCookie.get().getValue());

                if(!StringUtils.hasText(refreshTokenValue)) {
                    throw new JwtAuthenticationException("Request does not contain valid Refresh token!");
                }

                try {
                    var refreshToken = refreshTokenService.getToken(refreshTokenValue);
                    var newRefreshToken = refreshTokenService.regenerateToken(refreshToken);

                    var setAccessCookie = cookieProvider.createAccessCookie(newRefreshToken.getUser());
                    accessToken = setAccessCookie.getValue();

                    var setRefreshCookie = cookieProvider.updateRefreshCookie(newRefreshToken);

                    response.addHeader(HttpHeaders.SET_COOKIE, setAccessCookie.toString());
                    response.addHeader(HttpHeaders.SET_COOKIE, setRefreshCookie.toString());
                } catch (RefreshTokenNotFoundException ex) {
                    throw new JwtAuthenticationException("Request does not contain valid Refresh token!");
                }
            }

            var accessEmail = tokenProvider.getClaims(accessToken).getSubject();

            var userDetails = userService.loadUserByUsername(accessEmail);
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtAuthenticationException |
                 RuntimeException ex) {
            log.warn(ex.getMessage());

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    static class JwtAuthenticationException extends Exception {
        public JwtAuthenticationException(String message) {
            super(message);
        }
    }
}
