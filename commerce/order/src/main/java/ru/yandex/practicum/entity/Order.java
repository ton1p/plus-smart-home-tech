package ru.yandex.practicum.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.dto.order.OrderState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    private String username;

    private UUID shoppingCartId;

    @ElementCollection
    @CollectionTable(name = "product_quantity", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_quantity")
    @MapKeyColumn(name = "product_id")
    private Map<UUID, Integer> products = new HashMap<>();

    private UUID paymentId;

    private UUID deliveryId;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private Float deliveryWeight;

    private Float deliveryVolume;

    private Boolean fragile;

    private Float totalPrice;

    private Float deliveryPrice;

    private Float productPrice;
}
