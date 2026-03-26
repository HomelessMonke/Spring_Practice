package homeless.monkey.com.data_task2.repository;

import homeless.monkey.com.data_task2.entity.EmployeeEntity;
import homeless.monkey.com.data_task2.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    List<EmployeeProjection> findAllBy();

    Optional<EmployeeProjection> findProjectedById(Long id);
}
