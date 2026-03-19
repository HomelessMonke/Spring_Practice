package homeless.monkey.com.mvc_task2.dto;

import jakarta.validation.constraints.NotBlank;

public record BookDto(
        @NotBlank(message = "Поле обязательное")
        String bookName,

        @NotBlank(message = "Поле обязательное")
        String authorName
) {}

