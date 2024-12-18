package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductToWarehouseRequest {
    @NotNull
    @NotBlank
    private String productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
