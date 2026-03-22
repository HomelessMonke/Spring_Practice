package homeless.monkey.com.mvc_task3.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id){
        super("Order not found with id: " + id);
    }
}
