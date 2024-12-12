package ru.yandex.practicum.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.product.CreateUpdateProductDto;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.dto.product.QuantityState;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> findAllByCategory(@RequestParam ProductCategory category, Pageable pageable) {
        return productService.findAllByCategory(category, pageable);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid @RequestBody CreateUpdateProductDto createUpdateProductDto) {
        return productService.create(createUpdateProductDto);
    }

    @PostMapping
    public ProductDto update(@Valid @RequestBody CreateUpdateProductDto createUpdateProductDto) {
        return productService.update(createUpdateProductDto);
    }

    @PostMapping("/removeProductFromStore")
    public void deactivateProduct(@RequestBody String productId) {
        productService.deactivateProduct(productId);
    }

    @PostMapping("/quantityState")
    public void setQuantityState(@RequestParam String productId, @RequestParam QuantityState quantityState) {
        productService.setQuantityState(productId, quantityState);
    }

    @GetMapping("/{productId}")
    public ProductDto findById(@PathVariable String productId) {
        return productService.findById(productId);
    }

}
