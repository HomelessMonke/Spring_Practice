package homeless.monkey.com.mvc_task2.exception;

public class BookAlreadyExistException extends RuntimeException{
    public BookAlreadyExistException(String name){
        super("Book already exist: " + name);
    }
}