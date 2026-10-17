package com.zhy.onlineorder.model;

import com.zhy.onlineorder.entity.CustomerEntity;

public record CustomerDto(
        Long id,
        String email,
        String firstName,
        String lastName
) {
    public CustomerDto(CustomerEntity entity) {
        this(entity.id(), entity.email(), entity.firstName(), entity.lastName());
    }
}