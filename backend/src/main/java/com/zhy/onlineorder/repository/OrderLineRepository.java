package com.zhy.onlineorder.repository;

import com.zhy.onlineorder.entity.OrderLineEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderLineRepository extends ListCrudRepository<OrderLineEntity, Long> {
    List<OrderLineEntity> getAllByOrderIdOrderByIdAsc(Long orderId);
}
