package homeless.monkey.com.data_task2.exception;

public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String name) {
        super("EmployeeEntity already exists: " + name);
    }
}
