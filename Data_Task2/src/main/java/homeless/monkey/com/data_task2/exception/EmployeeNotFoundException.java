package homeless.monkey.com.data_task2.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("EmployeeEntity not found with id:" + id);
    }
}