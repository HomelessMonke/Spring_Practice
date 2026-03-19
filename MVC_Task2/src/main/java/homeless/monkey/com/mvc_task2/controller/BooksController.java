package homeless.monkey.com.mvc_task2.controller;

import homeless.monkey.com.mvc_task2.dto.BookDto;
import homeless.monkey.com.mvc_task2.model.BookEntity;
import homeless.monkey.com.mvc_task2.service.BooksService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public ResponseEntity<Page<BookDto>> getAllBooks(@PageableDefault(size = 5, sort = "name") Pageable pageable){
        return ResponseEntity.ok(booksService.getPageBooks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id){
        return ResponseEntity.ok(booksService.getBook(id));
    }

    @PostMapping()
    public ResponseEntity<BookDto> createUser(@Valid @RequestBody BookDto dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(booksService.createBook(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateUser(@PathVariable Long id, @RequestBody BookDto dto){
        return ResponseEntity.ok(booksService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        booksService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
