package homeless.monkey.com.mvc_task3.dto;

import homeless.monkey.com.mvc_task3.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        @NotEmpty
        List<String> productsNames,

        @NotBlank
        String customerFullName,

        @NotNull
        LocalDateTime orderDate,

        @NotBlank
        String shippingAddress,

        @Positive
        BigDecimal totalPrice,

        @NotNull
        OrderStatus orderStatus
){}
