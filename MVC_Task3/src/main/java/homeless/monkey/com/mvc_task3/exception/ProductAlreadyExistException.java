package homeless.monkey.com.mvc_task3.exception;

public class ProductAlreadyExistException extends RuntimeException{
    public ProductAlreadyExistException(String name){
        super("Product already exists: " + name);
    }
}

