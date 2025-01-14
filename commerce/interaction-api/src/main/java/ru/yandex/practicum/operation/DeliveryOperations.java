package ru.yandex.practicum.operation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.util.UUID;

@FeignClient(name = "delivery", path = "/api/v1/delivery")
public interface DeliveryOperations {
    @PutMapping
    DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/successful")
    void successfulDelivery(@RequestParam UUID deliveryId);

    @PostMapping("/picked")
    void pickedUpDelivery(@RequestParam UUID deliveryId);

    @PostMapping("/failed")
    void failedDelivery(@RequestParam UUID deliveryId);

    @PostMapping("/cost")
    Float getCost(@RequestBody OrderDto orderDto);
}
