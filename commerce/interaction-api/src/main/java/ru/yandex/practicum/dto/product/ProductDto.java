package ru.yandex.practicum.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ProductDto {
    private String productId;
    private String productName;
    private String description;
    private String imageSrc;
    private QuantityState quantityState;
    private ProductState productState;
    private Long rating;
    private ProductCategory productCategory;
    private Double price;
}
