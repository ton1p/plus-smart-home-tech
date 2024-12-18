package ru.yandex.practicum.dto.warehouse;

import lombok.Data;

@Data
public class WarehouseDto {
    private Long id;

    private String productId;

    private Boolean fragile;

    private DimensionDto dimension;

    private Float weight;
}

