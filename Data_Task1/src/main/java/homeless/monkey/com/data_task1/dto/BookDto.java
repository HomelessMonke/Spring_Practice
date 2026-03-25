package homeless.monkey.com.data_task1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record BookDto (
        @NotBlank
        String title,

        @NotBlank
        String author,

        @Positive
        Integer publicationYear
){}
