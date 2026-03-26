package homeless.monkey.com.data_task2.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("Department not found with id=" + id);
    }
}
