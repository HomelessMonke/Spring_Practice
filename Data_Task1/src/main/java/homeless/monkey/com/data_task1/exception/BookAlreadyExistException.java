package homeless.monkey.com.data_task1.exception;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException(String title, String author) {
        super(String.format("Book already exist: %s %s", title, author));
    }
}

