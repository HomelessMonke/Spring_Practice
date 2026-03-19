package homeless.monkey.com.mvc_task1.repository;

import homeless.monkey.com.mvc_task1.model.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
