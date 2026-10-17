package com.zhy.onlineorder.controller;

import com.zhy.onlineorder.entity.CustomerEntity;
import com.zhy.onlineorder.model.AddToCartBody;
import com.zhy.onlineorder.model.CartDto;
import com.zhy.onlineorder.service.CartService;
import com.zhy.onlineorder.service.CustomerService;
import com.zhy.onlineorder.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;
    private final OrderService orderService;

    public CartController(CartService cartService, CustomerService customerService, OrderService orderService) {
        this.cartService = cartService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public CartDto getCart(@AuthenticationPrincipal User user) {
        return cartService.getCart(getCustomerId(user));
    }

    @PostMapping("/cart")
    public void addToCart(@AuthenticationPrincipal User user, @RequestBody AddToCartBody body) {
        cartService.addMenuItemToCart(getCustomerId(user), body.menuId());
    }

    @PostMapping("/cart/decrease")
    public void removeFromCart(@AuthenticationPrincipal User user, @RequestBody AddToCartBody body) {
        cartService.removeMenuItemFromCart(getCustomerId(user), body.menuId());
    }

    @PostMapping("/cart/checkout")
    public void checkout(@AuthenticationPrincipal User user) {
        orderService.placeOrder(getCustomerId(user));
    }
    private long getCustomerId(User user) {
        CustomerEntity customer = customerService.getCustomerByEmail(user.getUsername());
        return customer.id();
    }
}