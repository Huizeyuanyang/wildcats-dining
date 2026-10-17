package com.zhy.onlineorder.service;

import com.zhy.onlineorder.entity.*;
import com.zhy.onlineorder.model.*;
import com.zhy.onlineorder.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final CartService cartService;

    public OrderService(CartRepository cartRepository,
                        OrderItemRepository orderItemRepository,
                        MenuItemRepository menuItemRepository,
                        OrderRepository orderRepository,
                        OrderLineRepository orderLineRepository,
                        CartService cartService) {
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.cartService = cartService;
    }

    @Transactional
    public void placeOrder(long customerId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        List<OrderItemEntity> cartItems = orderItemRepository.getAllByCartIdOrderByIdAsc(cart.id());
        if (cartItems.isEmpty()) {
            return;
        }

        OrderEntity order = new OrderEntity(null, customerId, cart.totalPrice(), LocalDateTime.now());
        OrderEntity savedOrder = orderRepository.save(order);

        for (OrderItemEntity item : cartItems) {
            MenuItemEntity menuItem = menuItemRepository.findById(item.menuItemId()).get();
            OrderLineEntity line = new OrderLineEntity(
                    null,
                    item.menuItemId(),
                    savedOrder.id(),
                    item.price(),
                    item.quantity(),
                    menuItem.name()
            );
            orderLineRepository.save(line);
        }

        cartService.clearCart(customerId);
    }

    public List<OrderDto> getOrders(long customerId) {
        List<OrderEntity> orders = orderRepository.getAllByCustomerIdOrderByCreatedAtDesc(customerId);

        List<OrderDto> res = new ArrayList<>();

        for (OrderEntity order : orders) {
            List<OrderLineEntity> orderLines = orderLineRepository.getAllByOrderIdOrderByIdAsc(order.id());
            OrderDto orderDto = new OrderDto(order, orderLines);
            res.add(orderDto);
        }

        return res;
    }
}
