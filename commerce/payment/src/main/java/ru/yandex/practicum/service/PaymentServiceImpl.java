package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.payment.PaymentStatus;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.entity.Payment;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.operation.ShoppingStoreOperations;
import ru.yandex.practicum.repository.PaymentRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ShoppingStoreOperations shoppingStoreOperations;

    private Payment getPaymentById(UUID paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isEmpty()) {
            throw new NotFoundException("Payment not found");
        }
        return optionalPayment.get();
    }

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        final Payment payment = Payment.builder()
                .totalPayment(getTotalCost(orderDto))
                .deliveryTotal(orderDto.getDeliveryPrice())
                .feeTotal(getProductCost(orderDto) * 0.1f)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return PaymentMapper.INSTANCE.paymentToPaymentDto(paymentRepository.save(payment));
    }

    @Override
    public Float getTotalCost(OrderDto orderDto) {
        Float productCost = getProductCost(orderDto);
        Float fee = productCost * 0.1f;
        Float deliveryCost = orderDto.getDeliveryPrice();
        return productCost + fee + deliveryCost;
    }

    @Override
    public Float getProductCost(OrderDto orderDto) {
        AtomicReference<Float> total = new AtomicReference<>(0f);
        orderDto.getProducts().forEach((key, value) -> {
            ProductDto productDto = shoppingStoreOperations.findById(key.toString());
            total.updateAndGet(v -> v + (productDto.getPrice().floatValue() * value));
        });
        return total.get();
    }

    @Override
    public void successfulPayment(UUID paymentId) {
        Payment payment = getPaymentById(paymentId);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
    }

    @Override
    public void failedPayment(UUID paymentId) {
        Payment payment = getPaymentById(paymentId);
        payment.setPaymentStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
    }
}
