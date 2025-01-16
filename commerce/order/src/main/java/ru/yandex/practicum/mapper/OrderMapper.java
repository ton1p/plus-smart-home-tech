package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);

    List<OrderDto> ordersToOrderDtos(List<Order> orders);

    List<Order> orderDtosToOrders(List<OrderDto> orderDtos);
}
