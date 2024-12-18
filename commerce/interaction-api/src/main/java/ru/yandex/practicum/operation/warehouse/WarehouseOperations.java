package ru.yandex.practicum.operation.warehouse;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;

@FeignClient(value = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseOperations {
    @PostMapping("/check")
    BookedProductsDto checkCart(@RequestBody @Valid CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse;
}
