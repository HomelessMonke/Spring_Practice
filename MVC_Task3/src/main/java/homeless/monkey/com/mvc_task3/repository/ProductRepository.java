package homeless.monkey.com.mvc_task3.repository;

import homeless.monkey.com.mvc_task3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
