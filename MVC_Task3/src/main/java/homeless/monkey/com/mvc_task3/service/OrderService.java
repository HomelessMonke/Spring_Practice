package homeless.monkey.com.mvc_task3.service;

import homeless.monkey.com.mvc_task3.dto.OrderCreationRequestDto;
import homeless.monkey.com.mvc_task3.dto.OrderResponseDto;
import homeless.monkey.com.mvc_task3.entity.Customer;
import homeless.monkey.com.mvc_task3.entity.Order;
import homeless.monkey.com.mvc_task3.entity.Product;
import homeless.monkey.com.mvc_task3.enums.OrderStatus;
import homeless.monkey.com.mvc_task3.exception.OrderNotFoundException;
import homeless.monkey.com.mvc_task3.mapper.OrderMapper;
import homeless.monkey.com.mvc_task3.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, ProductService productService, CustomerService customerService) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderCreationRequestDto requestDto) {
        List<Product> products = productService.getProductsByIds(requestDto.productsIds());
        Customer customer = customerService.getCustomer(requestDto.customerId());

        Order order = new Order();
        order.setProducts(products);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());
        order.setTotalPrice(getTotalPrice(products));
        order.setOrderStatus(OrderStatus.CREATED);

        orderRepository.save(order);
        return orderMapper.toResponseDto(order);
    }

    private BigDecimal getTotalPrice(List<Product> products){
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderResponseDto getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return orderMapper.toResponseDto(order);
    }
}
