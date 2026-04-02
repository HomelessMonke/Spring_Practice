package homeless.monkey.com.security_task2.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank String username,
    @NotBlank String password
){}