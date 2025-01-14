package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.exception.ErrorHandler;
import ru.yandex.practicum.operation.PaymentOperations;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentController extends ErrorHandler implements PaymentOperations {
    private final PaymentService paymentService;

    @Override
    @PostMapping
    public PaymentDto createPayment(@RequestBody OrderDto orderDto) {
        return paymentService.createPayment(orderDto);
    }

    @Override
    @PostMapping("/totalCost")
    public Float getTotalCost(@RequestBody OrderDto orderDto) {
        return paymentService.getTotalCost(orderDto);
    }

    @Override
    @PostMapping("/refund")
    public void successfulPayment(@RequestBody UUID paymentId) {
        paymentService.successfulPayment(paymentId);
    }

    @Override
    @PostMapping("/productCost")
    public Float getProductCost(@RequestBody OrderDto orderDto) {
        return paymentService.getProductCost(orderDto);
    }

    @Override
    @PostMapping("/failed")
    public void failedPayment(@RequestBody UUID paymentId) {
        paymentService.failedPayment(paymentId);
    }
}
