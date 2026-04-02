package homeless.monkey.com.security_task2.dto;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
){}
