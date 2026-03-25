package homeless.monkey.com.data_task1.repository;

import homeless.monkey.com.data_task1.dto.BookDto;
import homeless.monkey.com.data_task1.exception.BookNotFoundException;
import homeless.monkey.com.data_task1.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book b = new Book();
        b.setId(rs.getLong("id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setPublicationYear(rs.getInt("publication_year"));
        return b;
    };

    public Long save(Book book){
        String sql = "INSERT INTO books (title, author, publication_year) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublicationYear());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Book findById(Long id) {
        String sql = "SELECT * FROM books WHERE id=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Book updateBook(Long id, BookDto dto) {
        String sql = "UPDATE books SET title=?, author=?, publication_year=? WHERE id=?";
        int updated = jdbcTemplate.update(sql,
                dto.title(),
                dto.author(),
                dto.publicationYear(),
                id
        );

        if(updated == 0){
            throw new BookNotFoundException("Book already exist: %s" + id);
        }

        return findById(id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM books WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
