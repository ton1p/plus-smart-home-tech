package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.util.UUID;

public interface DeliveryService {
    DeliveryDto createDelivery(DeliveryDto deliveryDto);

    void successfulDelivery(UUID deliveryId);

    void pickedUpDelivery(UUID deliveryId);

    void failedDelivery(UUID deliveryId);

    Float getCost(OrderDto orderDto);
}
