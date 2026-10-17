package com.zhy.onlineorder.repository;

import com.zhy.onlineorder.entity.MenuItemEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends ListCrudRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> getByRestaurantId(Long restaurantid);
}
