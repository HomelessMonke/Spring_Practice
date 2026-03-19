package homeless.monkey.com.mvc_task1.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String email){
        super("Email already exist: " + email);
    }
}

