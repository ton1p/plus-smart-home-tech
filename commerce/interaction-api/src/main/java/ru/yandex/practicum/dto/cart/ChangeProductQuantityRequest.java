package ru.yandex.practicum.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeProductQuantityRequest {
    @NotNull
    private String productId;

    @Min(0)
    private Integer newQuantity;
}
