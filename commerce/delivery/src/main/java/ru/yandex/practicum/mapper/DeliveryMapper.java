package ru.yandex.practicum.mapper;

import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.entity.Delivery;

public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    DeliveryDto deliveryToDeliveryDto(Delivery delivery);
    
    Delivery deliveryDtoToDelivery(DeliveryDto deliveryDto);
}
