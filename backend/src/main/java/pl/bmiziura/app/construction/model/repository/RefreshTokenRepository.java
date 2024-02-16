package pl.bmiziura.app.construction.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bmiziura.app.construction.model.entity.RefreshTokenEntity;
import pl.bmiziura.app.user.domain.model.RefreshToken;

import java.sql.Ref;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    @Query(value = "SELECT t FROM RefreshTokenEntity t INNER JOIN t.user u WHERE t.id = :id AND t.expireTime >= CURRENT_DATE AND t.status = 'ACTIVE'")
    Optional<RefreshTokenEntity> findByIdNotExpired(Long id);

    @Query(value = "SELECT t FROM RefreshTokenEntity t INNER JOIN t.user u WHERE t.token = :token AND t.expireTime >= CURRENT_DATE AND t.status = 'ACTIVE'")
    Optional<RefreshTokenEntity> findByTokenNotExpired(String token);

    @Query(value = "SELECT t FROM RefreshTokenEntity t WHERE t.expireTime < CURRENT_DATE AND t.status = 'ACTIVE'")
    List<RefreshTokenEntity> findAllByTokenExpired();
}
