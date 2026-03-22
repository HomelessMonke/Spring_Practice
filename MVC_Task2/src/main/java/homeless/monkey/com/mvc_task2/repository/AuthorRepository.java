package homeless.monkey.com.mvc_task2.repository;

import homeless.monkey.com.mvc_task2.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByName(String name);
}
