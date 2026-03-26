package homeless.monkey.com.data_task2.repository;

import homeless.monkey.com.data_task2.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    boolean existsByName(String name);
}
