package pl.bmiziura.app.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bmiziura.app.infrastructure.config.security.properties.TokenProperties;
import pl.bmiziura.app.infrastructure.config.security.providers.JwtTokenProvider;

@Configuration
@RequiredArgsConstructor
public class JwtConfiguration {
    private final TokenProperties tokenProperties;

    @Bean(name = "refreshTokenProvider")
    public JwtTokenProvider refreshTokenProvider() {
        return new JwtTokenProvider(tokenProperties.getRefreshValidityTime(), tokenProperties.getRefreshSecret());
    }

    @Bean(name = "accessTokenProvider")
    public JwtTokenProvider accessTokenProvider() {
        return new JwtTokenProvider(tokenProperties.getAccessValidityTime(), tokenProperties.getAccessSecret());
    }
}
