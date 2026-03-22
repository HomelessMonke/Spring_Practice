package homeless.monkey.com.mvc_task1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank(message = "Поле обязательное")
        String name,

        @Email(message = "Некорректный формат email")
        @NotBlank(message = "Поле обязательное")
        String email
) {}
