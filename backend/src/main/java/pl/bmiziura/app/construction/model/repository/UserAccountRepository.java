package pl.bmiziura.app.construction.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);

    Optional<UserAccountEntity> findByEmailAndPassword(String email, String password);
}
