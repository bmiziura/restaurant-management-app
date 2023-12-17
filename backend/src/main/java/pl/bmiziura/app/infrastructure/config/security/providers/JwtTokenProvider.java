package pl.bmiziura.app.infrastructure.config.security.providers;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.domain.model.UserRole;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final long validityTime;
    private final String secret;

    public String createToken(UserAccount user) {
        var createTime = LocalDateTime.now();
        var expiryDate = createTime.plusSeconds(validityTime);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRoles().stream().map(UserRole::toString).collect(Collectors.toSet()))
                .setIssuedAt(Timestamp.valueOf(createTime))
                .setExpiration(Timestamp.valueOf(expiryDate))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parse(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT Signature", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT Token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT Token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT Token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty", ex);
        }

        return false;
    }
}