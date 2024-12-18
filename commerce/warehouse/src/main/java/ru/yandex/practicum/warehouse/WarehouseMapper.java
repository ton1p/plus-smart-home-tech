package ru.yandex.practicum.warehouse;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.WarehouseDto;

@Mapper
public interface WarehouseMapper {
    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    Warehouse newProductInWarehouseRequestToWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest);

    WarehouseDto warehouseToWarehouseDto(Warehouse warehouse);
}
