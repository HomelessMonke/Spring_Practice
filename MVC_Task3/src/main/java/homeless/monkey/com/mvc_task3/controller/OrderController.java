package homeless.monkey.com.mvc_task3.controller;

import homeless.monkey.com.mvc_task3.dto.OrderCreationRequestDto;
import homeless.monkey.com.mvc_task3.dto.OrderResponseDto;
import homeless.monkey.com.mvc_task3.service.OrderService;
import homeless.monkey.com.mvc_task3.validator.ConstraintViolationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

@RequestMapping("/api/orders")
@Controller
public class OrderController {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    private final ConstraintViolationValidator validator;

    public OrderController(ObjectMapper objectMapper, OrderService orderService, ConstraintViolationValidator validator) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
        this.validator = validator;
    }

    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody String orderDtoJson){

        OrderCreationRequestDto creationRequestDto = objectMapper.readValue(orderDtoJson, OrderCreationRequestDto.class);
        validator.validate(creationRequestDto);

        OrderResponseDto orderResponseDto = orderService.createOrder(creationRequestDto);
        String responseJson = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(orderResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Long id){
        OrderResponseDto responseDto = orderService.getOrder(id);
        String responseJson = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(responseDto);

        return ResponseEntity.ok().body(responseJson);
    }
}
