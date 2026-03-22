package homeless.monkey.com.mvc_task3.controller;

import homeless.monkey.com.mvc_task3.dto.ProductDto;
import homeless.monkey.com.mvc_task3.service.ProductService;
import homeless.monkey.com.mvc_task3.validator.ConstraintViolationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RequestMapping("/api/products")
@Controller
public class ProductController {

    private final ConstraintViolationValidator validator;
    private final ObjectMapper objectMapper;
    private final ProductService productService;

    public ProductController(ConstraintViolationValidator validator, ObjectMapper objectMapper, ProductService productService) {
        this.validator = validator;
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody String productDtoJson) {
        ProductDto productDto = objectMapper.readValue(productDtoJson, ProductDto.class);
        validator.validate(productDto);

        ProductDto responseDto = productService.createProduct(productDto);
        var jsonResponse = objectMapper.writerWithDefaultPrettyPrinter()
                                        .writeValueAsString(responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);
    }

    @GetMapping
    public ResponseEntity<String> getAllProducts(){
        List<ProductDto> allProducts = productService.getAllProducts();
        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter()
                                            .writeValueAsString(allProducts);
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable Long id){
        ProductDto dto = productService.getProduct(id);
        var json = objectMapper.writerWithDefaultPrettyPrinter()
                                .writeValueAsString(dto);
        return ResponseEntity.ok(json);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody String productDtoJson){

        ProductDto productDto = objectMapper.readValue(productDtoJson, ProductDto.class);
        validator.validate(productDto);

        ProductDto dto = productService.updateProduct(id, productDto);
        var json = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(dto);
        return ResponseEntity.ok(json);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
