package ru.yandex.practicum.operation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;

import java.util.List;
import java.util.Map;

@FeignClient(name = "shopping-cart", path = "/api/v1/shopping-cart")
public interface CartOperations {
    @GetMapping
    CartDto getCartByUsername(@RequestParam String username);

    @PutMapping
    CartDto addItemsToCart(@RequestParam String username, @RequestBody Map<String, Integer> products);

    @DeleteMapping
    void deactivateCart(@RequestParam String username);

    @PostMapping("/remove")
    CartDto removeItems(@RequestParam String username, @RequestBody List<String> products);

    @PostMapping("/change-quantity")
    CartDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest changeProductQuantityRequest);

    @PostMapping("/booking")
    BookedProductsDto bookingProducts(@RequestParam String username);
}
