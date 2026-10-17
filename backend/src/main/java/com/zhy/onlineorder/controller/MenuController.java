package com.zhy.onlineorder.controller;


import com.zhy.onlineorder.entity.MenuItemEntity;
import com.zhy.onlineorder.model.CustomerDto;
import com.zhy.onlineorder.model.RestaurantDto;
import com.zhy.onlineorder.service.MenuItemService;
import com.zhy.onlineorder.service.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.zhy.onlineorder.entity.CustomerEntity;
import com.zhy.onlineorder.model.CustomerDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
public class MenuController {
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    public MenuController(RestaurantService restaurantService, MenuItemService menuItemService) {
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    @GetMapping("/restaurant/{restaurantId}/menu")
    public List<MenuItemEntity> getMenuByRestaurant(@PathVariable("restaurantId") long restaurantId) {
        return menuItemService.getMenuItemsByRestaurantId(restaurantId);
    }

    @GetMapping("/restaurants/menu")
    public List<RestaurantDto> getMenuForAllRestaurants() {
        return restaurantService.getRestaurants();
    }

}
