package ru.yandex.practicum.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private UUID orderId;
    
    private UUID shoppingCartId;

    private Map<UUID, Integer> products;

    private UUID paymentId;

    private UUID deliveryId;

    private OrderState state;

    private Float deliveryWeight;

    private Float deliveryVolume;

    private Boolean fragile;

    private Float totalPrice;

    private Float deliveryPrice;

    private Float productPrice;
}
