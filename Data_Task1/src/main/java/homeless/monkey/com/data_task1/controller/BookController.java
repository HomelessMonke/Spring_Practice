package homeless.monkey.com.data_task1.controller;

import homeless.monkey.com.data_task1.dto.BookDto;
import homeless.monkey.com.data_task1.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Long> createBook(@RequestBody BookDto dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.createBook(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto dto){
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
