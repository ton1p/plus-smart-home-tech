package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewProductInWarehouseRequest {
    @NotNull
    @NotBlank
    private String productId;

    @NotNull
    private Boolean fragile;

    @NotNull
    private DimensionDto dimension;

    @NotNull
    @Min(1)
    private Float weight;
}
