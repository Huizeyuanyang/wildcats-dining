package com.zhy.onlineorder.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("order_lines")
public record OrderLineEntity(
        @Id Long id,
        Long menuItemId,
        Long orderId,
        Double price,
        Integer quantity,
        String menuItemName
) {
}