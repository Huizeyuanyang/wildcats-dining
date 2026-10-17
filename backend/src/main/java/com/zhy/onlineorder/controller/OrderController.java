package com.zhy.onlineorder.controller;

import com.zhy.onlineorder.entity.CustomerEntity;
import com.zhy.onlineorder.model.OrderDto;
import com.zhy.onlineorder.service.CustomerService;
import com.zhy.onlineorder.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders(@AuthenticationPrincipal User user) {
        return orderService.getOrders(getCustomerId(user));
    }

    private long getCustomerId(User user) {
        CustomerEntity customer = customerService.getCustomerByEmail(user.getUsername());
        return customer.id();
    }
}