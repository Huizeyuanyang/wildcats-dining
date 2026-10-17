package com.zhy.onlineorder.model;

import com.zhy.onlineorder.entity.OrderEntity;
import com.zhy.onlineorder.entity.OrderLineEntity;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Double totalPrice,
        LocalDateTime createdAt,
        List<OrderLineEntity> lines
) {
    public OrderDto(OrderEntity order, List<OrderLineEntity> lines) {
        this(order.id(), order.totalPrice(), order.createdAt(), lines);
    }
}