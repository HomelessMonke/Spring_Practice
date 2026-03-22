package homeless.monkey.com.mvc_task3.mapper;

import homeless.monkey.com.mvc_task3.dto.ProductDto;
import homeless.monkey.com.mvc_task3.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productId", ignore = true)
    Product mapToProduct(ProductDto dto);

    ProductDto mapToDto(Product product);
}
