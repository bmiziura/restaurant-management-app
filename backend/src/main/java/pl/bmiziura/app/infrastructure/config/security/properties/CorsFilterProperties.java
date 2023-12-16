package pl.bmiziura.app.infrastructure.config.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = CorsFilterProperties.PREFIX)
public class CorsFilterProperties {
    public static final String PREFIX = "security.cors";
    private List<String> urlPatterns;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;
    private Long maxAge;
}
