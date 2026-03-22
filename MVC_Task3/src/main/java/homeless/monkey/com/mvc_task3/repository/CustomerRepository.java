package homeless.monkey.com.mvc_task3.repository;

import homeless.monkey.com.mvc_task3.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
