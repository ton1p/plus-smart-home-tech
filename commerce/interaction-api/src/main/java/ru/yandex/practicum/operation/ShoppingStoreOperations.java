package ru.yandex.practicum.operation;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.dto.product.CreateUpdateProductDto;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.QuantityState;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreOperations {
    @GetMapping
    Page<ProductDto> findAllByCategory(@RequestParam ProductCategory category, Pageable pageable);

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDto create(@Valid @RequestBody CreateUpdateProductDto createUpdateProductDto);

    @PostMapping
    ProductDto update(@Valid @RequestBody CreateUpdateProductDto createUpdateProductDto);

    @PostMapping("/removeProductFromStore")
    void deactivateProduct(@RequestBody String productId);

    @PostMapping("/quantityState")
    void setQuantityState(@RequestParam String productId, @RequestParam QuantityState quantityState);

    @GetMapping("/{productId}")
    ProductDto findById(@PathVariable String productId);
}
