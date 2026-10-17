package com.zhy.onlineorder.repository;

import com.zhy.onlineorder.entity.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Long> {
    List<OrderEntity> getAllByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
