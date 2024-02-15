package pl.bmiziura.app.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.bmiziura.app.infrastructure.config.security.filter.JwtRequestFilter;
import pl.bmiziura.app.infrastructure.config.security.properties.CorsFilterProperties;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilterProperties corsProperties;
    private final JwtRequestFilter jwtRequestFilter;

    private static final String[] SWAGGER_PATHS = {
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    private static final String[] ALL_POST_PATHS = {
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/token/activate",
            "/api/recovery/**",
            "/api/token/**"
    };

    private static final String[] ALL_GET_PATHS = {
            "/api/recovery",
            "/api/token/**"
    };

    private static final String[] AUTHORIZED_GET_PATHS = {
            "/api/auth/me"
    };

    private static final String[] AUTHORIZED_POST_PATHS = {
            "/api/auth/logout",
            "/api/auth/token/retry"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configure(http));
        http.csrf(AbstractHttpConfigurer::disable);

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers(HttpMethod.GET, SWAGGER_PATHS).permitAll();
            authorize.requestMatchers(HttpMethod.POST, ALL_POST_PATHS).permitAll();
            authorize.requestMatchers(HttpMethod.GET, ALL_GET_PATHS).permitAll();
            authorize.requestMatchers(HttpMethod.GET, AUTHORIZED_GET_PATHS).authenticated();
            authorize.requestMatchers(HttpMethod.POST, AUTHORIZED_POST_PATHS).authenticated();

            authorize.anyRequest().denyAll();
        });

        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(corsProperties.getAllowedOrigins());
        config.setAllowedHeaders(corsProperties.getAllowedHeaders());
        config.setAllowedMethods(corsProperties.getAllowedMethods());
        config.setAllowCredentials(true);
        config.setMaxAge(corsProperties.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
