package homeless.monkey.com.mvc_task3.service;

import homeless.monkey.com.mvc_task3.dto.ProductDto;
import homeless.monkey.com.mvc_task3.entity.Product;
import homeless.monkey.com.mvc_task3.exception.ProductAlreadyExistException;
import homeless.monkey.com.mvc_task3.exception.ProductNotFoundException;
import homeless.monkey.com.mvc_task3.mapper.ProductMapper;
import homeless.monkey.com.mvc_task3.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDto createProduct(ProductDto dto) {
        if(productRepository.existsByName((dto.name())))
            throw new ProductAlreadyExistException(dto.name());

        Product product = productMapper.mapToProduct(dto);
        return productMapper.mapToDto(productRepository.save(product));
    }


    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(productMapper::mapToDto).toList();
    }

    public List<Product> getProductsByIds(List<Long> ids){
        List<Product> products = productRepository.findAllById(ids);
        if(products.size() != ids.size()){
            var missingIds = products.stream()
                    .map(Product::getProductId)
                    .filter(productId -> !ids.contains(productId)).toList();

            throw new ProductNotFoundException("Product not found with ids: " + missingIds);
        }

        return products;
    }

    public ProductDto getProduct(Long id) {
        Product product = getProductInternal(id);
        return productMapper.mapToDto(product);
    }

    private Product getProductInternal(Long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found id: " + id));
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = getProductInternal(id);
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantityInStock(dto.quantityInStock());
        return productMapper.mapToDto(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        Product product = getProductInternal(id);
        productRepository.delete(product);
    }
}
