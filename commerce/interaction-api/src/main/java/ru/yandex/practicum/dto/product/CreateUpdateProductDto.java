package ru.yandex.practicum.dto.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUpdateProductDto {
    private String productId;

    @NotNull
    @NotBlank
    private String productName;

    @NotNull
    @NotBlank
    private String description;

    private String imageSrc;

    @NotNull
    private QuantityState quantityState;

    @NotNull
    private ProductState productState;

    @Min(1)
    @Max(5)
    private Long rating;

    @NotNull
    private ProductCategory productCategory;

    @NotNull
    private Double price;
}
