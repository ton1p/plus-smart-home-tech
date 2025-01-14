package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.OrderState;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.entity.Order;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.operation.CartOperations;
import ru.yandex.practicum.operation.DeliveryOperations;
import ru.yandex.practicum.operation.PaymentOperations;
import ru.yandex.practicum.operation.WarehouseOperations;
import ru.yandex.practicum.repository.OrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartOperations cartOperations;
    private final WarehouseOperations warehouseOperations;
    private final PaymentOperations paymentOperations;
    private final DeliveryOperations deliveryOperations;

    @Override
    public List<OrderDto> getByUsername(String username) {
        return OrderMapper.INSTANCE.ordersToOrderDtos(orderRepository.findAllByUsername(username));
    }

    @Override
    public OrderDto createNewOrder(CreateNewOrderRequest createNewOrderRequest) {
        CartDto cartDto = createNewOrderRequest.getShoppingCart();
        String username = cartOperations.getCartUsername(cartDto.getShoppingCartId());
        Map<UUID, Integer> products = new HashMap<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : cartDto.getProducts().entrySet()) {
            products.put(UUID.fromString(stringIntegerEntry.getKey()), stringIntegerEntry.getValue());
        }
        Order order = Order.builder()
                .username(username)
                .shoppingCartId(cartDto.getShoppingCartId())
                .state(OrderState.NEW)
                .products(products)
                .build();
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    private Order getById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public OrderDto returnProducts(ProductReturnRequest productReturnRequest) {
        Order order = getById(productReturnRequest.getOrderId());
        order.setState(OrderState.PRODUCT_RETURNED);
        productReturnRequest.getProducts().forEach((productId, quantity) -> {
            if (order.getProducts().containsKey(productId)) {
                order.getProducts().compute(productId, (k, q) -> Math.max(q - quantity, 0));
            }
        });
        Order saved = orderRepository.save(order);
        warehouseOperations.returnProducts(productReturnRequest.getProducts());
        return OrderMapper.INSTANCE.orderToOrderDto(saved);
    }

    @Override
    public OrderDto payment(UUID orderId) {
        Order order = getById(orderId);
        order.setState(OrderState.ON_PAYMENT);
        OrderDto orderDto = OrderMapper.INSTANCE.orderToOrderDto(order);
        PaymentDto paymentDto = paymentOperations.createPayment(orderDto);
        order.setPaymentId(paymentDto.getPaymentId());
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto paymentFailed(UUID orderId) {
        Order order = getById(orderId);
        order.setState(OrderState.PAYMENT_FAILED);
        paymentOperations.failedPayment(order.getPaymentId());
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto delivery(UUID orderId) {
        Order order = getById(orderId);
        order.setState(OrderState.ON_DELIVERY);
        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryState(DeliveryState.CREATED)
                .orderId(orderId)
                .build();
        DeliveryDto delivery = deliveryOperations.createDelivery(deliveryDto);
        order.setDeliveryId(delivery.getDeliveryId());
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto deliveryFailed(UUID orderId) {
        Order order = getById(orderId);
        order.setState(OrderState.DELIVERY_FAILED);
        deliveryOperations.failedDelivery(order.getDeliveryId());
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto completed(UUID orderId) {
        Order order = getById(orderId);
        order.setState(OrderState.COMPLETED);
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto calculateTotal(UUID orderId) {
        Order order = getById(orderId);
        OrderDto orderDto = OrderMapper.INSTANCE.orderToOrderDto(order);

        Float deliveryCost = deliveryOperations.getCost(orderDto);
        Float productCost = paymentOperations.getProductCost(orderDto);
        Float totalCost = paymentOperations.getTotalCost(orderDto);

        order.setDeliveryPrice(deliveryCost);
        order.setProductPrice(productCost);
        order.setTotalPrice(totalCost);

        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto calculateDelivery(UUID orderId) {
        Order order = getById(orderId);
        OrderDto orderDto = OrderMapper.INSTANCE.orderToOrderDto(order);
        Float deliveryCost = deliveryOperations.getCost(orderDto);
        order.setDeliveryPrice(deliveryCost);
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto assembly(UUID orderId) {
        Order order = getById(orderId);
        order.setState(OrderState.ASSEMBLED);
        AssemblyProductsForOrderRequest assemblyProductsForOrderRequest = AssemblyProductsForOrderRequest.builder()
                .orderId(orderId)
                .products(order.getProducts())
                .build();
        BookedProductsDto bookedProductsDto = warehouseOperations.assemblyProducts(assemblyProductsForOrderRequest);
        order.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        order.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
        order.setFragile(bookedProductsDto.getFragile());
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto assemblyFailed(UUID orderId) {
        Order order = getById(orderId);
        order.setState(OrderState.ASSEMBLY_FAILED);
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.save(order));
    }
}
