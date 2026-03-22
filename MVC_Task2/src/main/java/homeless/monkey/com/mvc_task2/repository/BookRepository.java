package homeless.monkey.com.mvc_task2.repository;

import homeless.monkey.com.mvc_task2.dto.BookDto;
import homeless.monkey.com.mvc_task2.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long>{
    boolean existsByName(String name);
}
