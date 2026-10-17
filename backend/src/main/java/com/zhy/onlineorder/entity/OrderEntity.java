package com.zhy.onlineorder.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("orders")
public record OrderEntity(
        @Id Long id,
        Long customerId,
        Double totalPrice,
        LocalDateTime createdAt
) {
}