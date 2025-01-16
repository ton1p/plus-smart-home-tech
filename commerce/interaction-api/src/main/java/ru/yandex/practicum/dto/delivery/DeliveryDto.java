package ru.yandex.practicum.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.dto.warehouse.AddressDto;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDto {
    private UUID deliveryId;

    private AddressDto fromAddress;

    private AddressDto toAddress;

    private UUID orderId;

    private DeliveryState deliveryState;
}
