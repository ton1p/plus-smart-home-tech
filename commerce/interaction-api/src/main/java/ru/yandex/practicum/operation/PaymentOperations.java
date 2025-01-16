package ru.yandex.practicum.operation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

import java.util.UUID;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentOperations {
    @PostMapping
    PaymentDto createPayment(@RequestBody OrderDto orderDto);

    @PostMapping("/totalCost")
    Float getTotalCost(@RequestBody OrderDto orderDto);

    @PostMapping("/refund")
    void successfulPayment(@RequestBody UUID paymentId);

    @PostMapping("/productCost")
    Float getProductCost(@RequestBody OrderDto orderDto);

    @PostMapping("/failed")
    void failedPayment(@RequestBody UUID paymentId);
}
