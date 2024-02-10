package pl.bmiziura.app.construction.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bmiziura.app.construction.model.MailTokenType;
import pl.bmiziura.app.construction.model.entity.MailTokenEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailTokenRepository extends JpaRepository<MailTokenEntity, Long> {
    @Query(value = "SELECT t.* FROM mail_tokens t LEFT JOIN users u ON u.id = t.user_account_id WHERE u.id = :id AND t.token = :token AND cast(t.type as text) = :#{#type.name()} AND t.expire_time > NOW() AND t.status = 'ACTIVE' LIMIT 1", nativeQuery = true)
    Optional<MailTokenEntity> findByUserIdAndTokenNotExpired(Long id, String token, MailTokenType type);

    @Query(value = "SELECT t.* FROM mail_tokens t WHERE t.expire_time < NOW() AND t.status = 'ACTIVE'", nativeQuery = true)
    List<MailTokenEntity> findAllByTokenExpired();

    @Query(value = "SELECT COUNT(t.*) FROM mail_tokens t WHERE t.user_account_id = :id AND cast(t.type as text) = :#{#type.name()} AND t.expire_time > NOW() AND t.status = 'ACTIVE'", nativeQuery = true)
    int countAllByUserAndTypeAndTokenNotExpired(Long id, MailTokenType type);
}
