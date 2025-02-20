package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.exception.ErrorHandler;
import ru.yandex.practicum.operation.CartOperations;
import ru.yandex.practicum.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-cart")
public class CartController extends ErrorHandler implements CartOperations {
    private final CartService cartService;

    @Override
    @GetMapping
    public CartDto getCartByUsername(@RequestParam String username) {
        return cartService.getCartByUsername(username);
    }

    @Override
    @PutMapping
    public CartDto addItemsToCart(@RequestParam String username, @RequestBody Map<String, Integer> products) {
        return cartService.addItemsToCart(username, products);
    }

    @Override
    @DeleteMapping
    public void deactivateCart(@RequestParam String username) {
        cartService.deactivateCart(username);
    }

    @Override
    @PostMapping("/remove")
    public CartDto removeItems(@RequestParam String username, @RequestBody List<String> products) {
        return cartService.removeItems(username, products);
    }

    @Override
    @PostMapping("/change-quantity")
    public CartDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest changeProductQuantityRequest) {
        return cartService.changeQuantity(username, changeProductQuantityRequest);
    }

    @Override
    @PostMapping("/booking")
    public BookedProductsDto bookingProducts(@RequestParam String username) {
        return cartService.bookingProducts(username);
    }

    @Override
    @GetMapping("/username")
    public String getCartUsername(UUID cartId) {
        return cartService.getCartUsername(cartId);
    }
}
