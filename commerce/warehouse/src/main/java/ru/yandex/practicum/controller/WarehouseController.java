package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.WarehouseDto;
import ru.yandex.practicum.exception.ErrorHandler;
import ru.yandex.practicum.operation.WarehouseOperations;
import ru.yandex.practicum.service.WarehouseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController extends ErrorHandler implements WarehouseOperations {
    private final WarehouseService warehouseService;

    @Override
    @PutMapping
    public WarehouseDto create(@RequestBody @Valid NewProductInWarehouseRequest body) {
        return warehouseService.create(body);
    }

    @Override
    @GetMapping("/address")
    public AddressDto getAddress() {
        return warehouseService.getAddress();
    }

    @Override
    @PostMapping("/add")
    public void addQuantity(@RequestBody @Valid AddProductToWarehouseRequest addProductToWarehouseRequest) {
        warehouseService.add(addProductToWarehouseRequest);
    }

    @Override
    @PostMapping("/check")
    public BookedProductsDto checkCart(@RequestBody @Valid CartDto cartDto) {
        return warehouseService.check(cartDto);
    }
}
