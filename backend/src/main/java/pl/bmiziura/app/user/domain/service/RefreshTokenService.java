package pl.bmiziura.app.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bmiziura.app.construction.model.RefreshTokenStatus;
import pl.bmiziura.app.construction.model.entity.RefreshTokenEntity;
import pl.bmiziura.app.construction.model.repository.RefreshTokenRepository;
import pl.bmiziura.app.exception.impl.RefreshTokenNotFoundException;
import pl.bmiziura.app.infrastructure.config.security.properties.TokenProperties;
import pl.bmiziura.app.user.domain.mapper.RefreshTokenMapper;
import pl.bmiziura.app.user.domain.model.RefreshToken;
import pl.bmiziura.app.user.domain.model.UserAccount;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;
    private final TokenProperties tokenProperties;

    public RefreshToken createToken(UserAccount userAccount) {
        var user = userService.getAccountEntity(userAccount.getId());

        var entity = new RefreshTokenEntity();
        entity.setToken(generateToken());
        entity.setExpireTime(LocalDateTime.now().plusMinutes(tokenProperties.getRefreshValidityTime()));
        entity.setUser(user);

        entity = refreshTokenRepository.save(entity);

        return refreshTokenMapper.toRefreshToken(entity);
    }

    public RefreshToken regenerateToken(RefreshToken refreshToken) {
        if(refreshToken == null) return null;

        var entity = getTokenEntity(refreshToken.getId());
        entity.setToken(generateToken());
        entity.setExpireTime(LocalDateTime.now().plusMinutes(tokenProperties.getRefreshValidityTime()));

        entity = refreshTokenRepository.save(entity);

        return refreshTokenMapper.toRefreshToken(entity);
    }

    public RefreshToken getToken(String token) {
        var refreshToken = getTokenEntity(token);

        return refreshTokenMapper.toRefreshToken(refreshToken);
    }

    public RefreshTokenEntity getTokenEntity(Long id) {
        return refreshTokenRepository.findByIdNotExpired(id)
                .orElseThrow(() -> new RefreshTokenNotFoundException(id));
    }
    public RefreshTokenEntity getTokenEntity(String token) {
        return refreshTokenRepository.findByTokenNotExpired(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException(token));
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void changeStatus(String token, RefreshTokenStatus status) {
        var tokenEntity = getTokenEntity(token);
        tokenEntity.setStatus(status);
        refreshTokenRepository.save(tokenEntity);
    }
}
