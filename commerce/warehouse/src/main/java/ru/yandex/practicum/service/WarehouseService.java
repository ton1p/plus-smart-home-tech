package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.WarehouseDto;

public interface WarehouseService {
    WarehouseDto create(NewProductInWarehouseRequest body);

    BookedProductsDto check(CartDto body);

    void add(AddProductToWarehouseRequest body);

    AddressDto getAddress();
}
