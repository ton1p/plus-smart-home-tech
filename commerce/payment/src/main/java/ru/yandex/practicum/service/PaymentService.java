package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

import java.util.UUID;

public interface PaymentService {
    PaymentDto createPayment(OrderDto orderDto);

    Float getTotalCost(OrderDto orderDto);

    Float getProductCost(OrderDto orderDto);

    void successfulPayment(UUID paymentId);

    void failedPayment(UUID paymentId);
}
