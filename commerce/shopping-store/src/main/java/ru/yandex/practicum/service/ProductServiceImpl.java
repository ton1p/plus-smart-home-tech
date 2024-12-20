package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.product.CreateUpdateProductDto;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.ProductState;
import ru.yandex.practicum.dto.product.QuantityState;
import ru.yandex.practicum.entity.Product;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductDto findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Product not found");
        }
        return ProductMapper.INSTANCE.productToProductDto(product.get());
    }

    @Transactional
    @Override
    public ProductDto create(CreateUpdateProductDto createProductDto) {
        String id = UUID.randomUUID().toString();
        createProductDto.setProductId(id);
        Product product = ProductMapper.INSTANCE.productToCreateUpdateProductDto(createProductDto);
        return ProductMapper.INSTANCE.productToProductDto(productRepository.save(product));
    }

    @Transactional
    @Override
    public ProductDto update(CreateUpdateProductDto updateProductDto) {
        Product found = ProductMapper.INSTANCE.productDtoToProduct(findById(updateProductDto.getProductId()));

        found.setProductName(updateProductDto.getProductName());
        found.setDescription(updateProductDto.getDescription());
        found.setImageSrc(updateProductDto.getImageSrc());
        found.setQuantityState(updateProductDto.getQuantityState());
        found.setProductState(updateProductDto.getProductState());
        found.setRating(updateProductDto.getRating());
        found.setProductCategory(updateProductDto.getProductCategory());
        found.setPrice(updateProductDto.getPrice());
        
        return ProductMapper.INSTANCE.productToProductDto(productRepository.save(found));
    }

    @Override
    public Page<ProductDto> findAllByCategory(ProductCategory category, Pageable pageable) {
        List<ProductDto> list = ProductMapper.INSTANCE.productsToProductDtoItems(productRepository.findAllByProductCategory(category, pageable));
        return new PageImpl<>(list, pageable, list.size());
    }

    @Transactional
    @Override
    public void deactivateProduct(String productId) {
        ProductDto found = findById(productId);
        found.setProductState(ProductState.DEACTIVATE);
        productRepository.save(ProductMapper.INSTANCE.productDtoToProduct(found));
    }

    @Transactional
    @Override
    public void setQuantityState(String productId, QuantityState quantityState) {
        ProductDto found = findById(productId);
        found.setQuantityState(quantityState);
        productRepository.save(ProductMapper.INSTANCE.productDtoToProduct(found));
    }
}
