package homeless.monkey.com.mvc_task3.mapper;

import homeless.monkey.com.mvc_task3.dto.OrderResponseDto;
import homeless.monkey.com.mvc_task3.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "productsNames", expression = "java(order.getProductNames())")
    @Mapping(target = "customerFullName", expression = "java(order.getCustomer().getFullName())")
    OrderResponseDto toResponseDto(Order order);
}
