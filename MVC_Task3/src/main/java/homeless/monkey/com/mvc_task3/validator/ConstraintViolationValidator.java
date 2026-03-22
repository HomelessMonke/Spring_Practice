package homeless.monkey.com.mvc_task3.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ConstraintViolationValidator {

    private final Validator validator;

    public ConstraintViolationValidator(Validator validator) {
        this.validator = validator;
    }

    public <T>void validate(T object){
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
