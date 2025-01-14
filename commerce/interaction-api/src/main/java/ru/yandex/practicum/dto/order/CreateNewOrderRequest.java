package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;

@Data
public class CreateNewOrderRequest {
    @NotNull
    CartDto shoppingCart;

    @NotNull
    AddressDto deliveryAddress;
}
