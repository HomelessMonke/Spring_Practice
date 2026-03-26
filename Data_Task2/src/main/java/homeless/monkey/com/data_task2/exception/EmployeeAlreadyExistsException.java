package homeless.monkey.com.data_task2.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String firstName, String lastName) {
        super(String.format("EmployeeEntity already exists: %s %s", firstName, lastName));
    }
}

