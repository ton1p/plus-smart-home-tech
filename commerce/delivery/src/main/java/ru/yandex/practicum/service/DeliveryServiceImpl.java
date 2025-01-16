package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.entity.Delivery;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.AddressMapper;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;

    private Delivery getById(final UUID id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Delivery with id " + id + " not found"));
    }

    @Override
    @Transactional
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = Delivery.builder()
                .fromAddress(AddressMapper.INSTANCE.addressDtoToAddress(deliveryDto.getFromAddress()))
                .toAddress(AddressMapper.INSTANCE.addressDtoToAddress(deliveryDto.getToAddress()))
                .orderId(deliveryDto.getOrderId())
                .deliveryState(DeliveryState.CREATED)
                .build();

        return DeliveryMapper.INSTANCE.deliveryToDeliveryDto(deliveryRepository.save(delivery));
    }

    @Override
    @Transactional
    public void successfulDelivery(UUID deliveryId) {
        Delivery delivery = getById(deliveryId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void pickedUpDelivery(UUID deliveryId) {
        Delivery delivery = getById(deliveryId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void failedDelivery(UUID deliveryId) {
        Delivery delivery = getById(deliveryId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        deliveryRepository.save(delivery);
    }

    @Override
    public Float getCost(OrderDto orderDto) {
        float total = 5f;
        Delivery delivery = getById(orderDto.getDeliveryId());
        float warehouseK;
        switch (delivery.getFromAddress().getStreet()) {
            case "ADDRESS_1": {
                warehouseK = 1f;
                break;
            }
            case "ADDRESS_2": {
                warehouseK = 2f;
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown street: " + delivery.getFromAddress().getStreet());
        }

        total += total * warehouseK;

        if (Boolean.TRUE.equals(orderDto.getFragile())) {
            total += total * 0.2f;
        }

        total += orderDto.getDeliveryWeight() * 0.3f;

        total += orderDto.getDeliveryVolume() * 0.2f;

        if (!delivery.getToAddress().getStreet().equals(delivery.getFromAddress().getStreet())) {
            total += total * 0.2f;
        }

        return total;
    }
}
