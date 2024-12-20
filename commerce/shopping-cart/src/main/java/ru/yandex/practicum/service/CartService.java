package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;

import java.util.List;
import java.util.Map;

public interface CartService {
    CartDto getCartByUsername(String username);

    CartDto addItemsToCart(String username, Map<String, Integer> products);

    void deactivateCart(String username);

    CartDto removeItems(String username, List<String> products);

    CartDto changeQuantity(String username, ChangeProductQuantityRequest changeProductQuantityRequest);

    BookedProductsDto bookingProducts(String username);
}
