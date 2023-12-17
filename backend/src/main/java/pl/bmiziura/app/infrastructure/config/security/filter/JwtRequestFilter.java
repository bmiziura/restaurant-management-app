package pl.bmiziura.app.infrastructure.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.bmiziura.app.infrastructure.config.security.providers.CookieProvider;
import pl.bmiziura.app.infrastructure.config.security.providers.JwtTokenProvider;
import pl.bmiziura.app.user.domain.service.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final CookieProvider cookieProvider;

    @Qualifier("refreshTokenProvider")
    private final JwtTokenProvider refreshTokenProvider;

    @Qualifier("accessTokenProvider")
    private final JwtTokenProvider accessTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var refreshCookie = cookieProvider.getRefreshCookie(request);
        var accessCookie = cookieProvider.getAccessCookie(request);

        try {
            if (refreshCookie.isEmpty())
                throw new JwtAuthenticationException("Request does not contain Refresh Cookie!");

            var refreshToken = refreshCookie.get().getValue();

            if (!StringUtils.hasText(refreshToken) || !refreshTokenProvider.validateToken(refreshToken))
                throw new JwtAuthenticationException("Request does not contain valid JWT Refresh token!");

            var refreshEmail = refreshTokenProvider.getClaims(refreshToken).getSubject();

            var accessToken = accessCookie.map(Cookie::getValue).orElse(null);
            if (!StringUtils.hasText(accessToken) || !accessTokenProvider.validateToken(accessToken)) {
                var user = userService.getUser(refreshEmail);

                var setCookie = cookieProvider.createAccessCookie(user);
                accessToken = setCookie.getValue();

                response.setHeader(HttpHeaders.SET_COOKIE, setCookie.toString());
            }

            var accessEmail = accessTokenProvider.getClaims(accessToken).getSubject();

            if (!refreshEmail.equals(accessEmail))
                throw new JwtAuthenticationException("Refresh & Access Token have different subjects!");

            var userDetails = userService.loadUserByUsername(accessEmail);
            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtAuthenticationException |
                 RuntimeException ex) { // todo change RuntimeException when adding custom exception for getting user
            log.warn(ex.getMessage());

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtToken(HttpServletRequest request, HttpServletResponse response) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    static class JwtAuthenticationException extends Exception {
        public JwtAuthenticationException(String message) {
            super(message);
        }
    }
}
