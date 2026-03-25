package homeless.monkey.com.data_task1.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(String.format(message));
    }
}
