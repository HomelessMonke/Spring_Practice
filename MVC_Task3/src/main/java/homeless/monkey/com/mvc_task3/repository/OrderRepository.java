package homeless.monkey.com.mvc_task3.repository;

import homeless.monkey.com.mvc_task3.entity.Customer;
import homeless.monkey.com.mvc_task3.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
