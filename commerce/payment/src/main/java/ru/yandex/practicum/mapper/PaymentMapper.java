package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.entity.Payment;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDto paymentToPaymentDto(Payment payment);

    Payment paymentDtoToPayment(PaymentDto paymentDto);
}
