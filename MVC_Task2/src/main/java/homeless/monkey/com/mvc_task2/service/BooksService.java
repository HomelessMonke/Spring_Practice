package homeless.monkey.com.mvc_task2.service;

import homeless.monkey.com.mvc_task2.dto.BookDto;
import homeless.monkey.com.mvc_task2.exception.BookAlreadyExistException;
import homeless.monkey.com.mvc_task2.exception.BookNotFoundException;
import homeless.monkey.com.mvc_task2.mapper.BookMapper;
import homeless.monkey.com.mvc_task2.model.AuthorEntity;
import homeless.monkey.com.mvc_task2.model.BookEntity;
import homeless.monkey.com.mvc_task2.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BooksService {

    private final int MAX_PAGE_SIZE = 100;

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookMapper bookMapper;

    public BooksService(BookRepository bookRepository, AuthorService authorService, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookDto createBook(BookDto dto) {
        if(bookRepository.existsByName(dto.bookName()))
            throw new BookAlreadyExistException(dto.bookName());

        AuthorEntity author = authorService.getOrCreate(dto.authorName());

        BookEntity book = new BookEntity();
        book.setName(dto.bookName());
        book.setAuthor(author);
        bookRepository.save(book);
        return bookMapper.toBookDto(book);
    }

    public BookDto getBook(Long id) {
        BookEntity book = findBook(id);
        AuthorEntity author = book.getAuthor();
        return new BookDto(book.getName(), author.getName());
    }

    public Page<BookDto> getPageBooks(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size too large");
        }

        Page<BookEntity> books = bookRepository.findAll(pageable);
        return books.map(bookMapper::toBookDto);
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto dto) {
        BookEntity book = findBook(id);
        AuthorEntity author = authorService.getOrCreate(dto.authorName());

        if (bookRepository.existsByName(dto.bookName()))
            throw new IllegalArgumentException("Can't update, book with same name already exists");

        book.setName(dto.bookName());
        book.setAuthor(author);
        bookRepository.save(book);
        return bookMapper.toBookDto(book);
    }

    public void deleteUser(Long id) {
        BookEntity book = findBook(id);
        bookRepository.delete(book);
    }

    private BookEntity findBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new BookNotFoundException("Can't find book with id:" + id));
    }
}
