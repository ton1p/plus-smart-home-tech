package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.exception.ErrorHandler;
import ru.yandex.practicum.operation.DeliveryOperations;
import ru.yandex.practicum.service.DeliveryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController extends ErrorHandler implements DeliveryOperations {
    private final DeliveryService deliveryService;

    @Override
    @PutMapping
    public DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.createDelivery(deliveryDto);
    }

    @Override
    @PostMapping("/successful")
    public void successfulDelivery(@RequestParam UUID deliveryId) {
        deliveryService.successfulDelivery(deliveryId);
    }

    @Override
    @PostMapping("/picked")
    public void pickedUpDelivery(@RequestParam UUID deliveryId) {
        deliveryService.pickedUpDelivery(deliveryId);
    }

    @Override
    @PostMapping("/failed")
    public void failedDelivery(@RequestParam UUID deliveryId) {
        deliveryService.failedDelivery(deliveryId);
    }

    @Override
    @PostMapping("/cost")
    public Float getCost(@RequestBody OrderDto orderDto) {
        return deliveryService.getCost(orderDto);
    }
}
