package ru.yandex.practicum.operation;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.WarehouseDto;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;

@FeignClient(value = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseOperations {
    @PutMapping
    WarehouseDto create(@RequestBody @Valid NewProductInWarehouseRequest body);

    @GetMapping("/address")
    AddressDto getAddress();

    @PostMapping("/add")
    void addQuantity(@RequestBody @Valid AddProductToWarehouseRequest addProductToWarehouseRequest);

    @PostMapping("/check")
    BookedProductsDto checkCart(@RequestBody @Valid CartDto cartDto) throws ProductInShoppingCartLowQuantityInWarehouse;
}
