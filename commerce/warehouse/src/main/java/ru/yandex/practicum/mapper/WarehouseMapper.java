package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.WarehouseDto;
import ru.yandex.practicum.entity.Warehouse;

@Mapper
public interface WarehouseMapper {
    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    Warehouse newProductInWarehouseRequestToWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest);

    WarehouseDto warehouseToWarehouseDto(Warehouse warehouse);
}
