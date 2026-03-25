package homeless.monkey.com.data_task1.service;

import homeless.monkey.com.data_task1.dto.BookDto;
import homeless.monkey.com.data_task1.exception.BookAlreadyExistException;
import homeless.monkey.com.data_task1.mapper.BookMapper;
import homeless.monkey.com.data_task1.model.Book;
import homeless.monkey.com.data_task1.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public Long createBook(BookDto dto){
        Book book = bookMapper.dtoToBook(dto);
        try {
            return bookRepository.save(book);
        }
        catch (DataIntegrityViolationException ex){
            throw new BookAlreadyExistException(dto.title(), dto.author());
        }
    }

    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::bookToDto)
                .toList();
    }

    public BookDto getBook(Long id) {
        return bookMapper.bookToDto(bookRepository.findById(id));
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto dto) {
        return bookMapper.bookToDto(bookRepository.updateBook(id, dto));
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
}
