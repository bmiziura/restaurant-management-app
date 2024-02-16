package pl.bmiziura.app.infrastructure.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class CipherConfig {
    private static final String ALGORITHM = "AES";

    @Value("${security.cipher}")
    private String cipherSecret;

    @Bean
    public SecretKey cipherKey() {
        var secret = Base64.getDecoder().decode(cipherSecret);

        return new SecretKeySpec(secret, ALGORITHM);
    }
}
