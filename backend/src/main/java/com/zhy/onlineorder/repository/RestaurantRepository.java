package com.zhy.onlineorder.repository;

import com.zhy.onlineorder.entity.RestaurantEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ListCrudRepository<RestaurantEntity, Long> {
}
