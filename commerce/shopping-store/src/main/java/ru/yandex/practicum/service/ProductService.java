package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.product.CreateUpdateProductDto;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.QuantityState;

public interface ProductService {
    ProductDto findById(String id);

    ProductDto create(CreateUpdateProductDto createProductDto);

    ProductDto update(CreateUpdateProductDto updateProductDto);

    Page<ProductDto> findAllByCategory(ProductCategory category, Pageable pageable);

    void deactivateProduct(String productId);

    void setQuantityState(String productId, QuantityState quantityState);
}
