package pl.bmiziura.app.construction.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bmiziura.app.construction.model.entity.MailTokenEntity;

import java.util.Optional;

@Repository
public interface MailTokenRepository extends JpaRepository<MailTokenEntity, Long> {
    @Query(value = "SELECT t.* FROM mail_tokens t LEFT JOIN users u ON u.id = t.id WHERE u.id = :id AND t.token = :token AND t.expire_time < NOW()::timestamp LIMIT 1", nativeQuery = true)
    Optional<MailTokenEntity> findByUserIdAndTokenNotExpired(Long id, String token);
}
