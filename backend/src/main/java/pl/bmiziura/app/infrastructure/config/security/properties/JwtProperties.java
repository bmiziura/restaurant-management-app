package pl.bmiziura.app.infrastructure.config.security.properties;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {
    public static final String PREFIX = "security.jwt";
    private int validityTime;
    private String secret;
}
