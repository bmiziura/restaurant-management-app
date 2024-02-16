package pl.bmiziura.app.infrastructure.config.security.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class TokenProperties {
    @Value("${security.refresh-token.validity-time}")
    private int refreshValidityTime;

    @Value("${security.access-token.validity-time}")
    private int accessValidityTime;
    @Value("${security.access-token.secret}")
    private String accessSecret;
}
