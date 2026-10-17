package com.zhy.onlineorder.service;


import com.zhy.onlineorder.entity.CartEntity;
import com.zhy.onlineorder.entity.MenuItemEntity;
import com.zhy.onlineorder.entity.OrderItemEntity;
import com.zhy.onlineorder.model.CartDto;
import com.zhy.onlineorder.model.OrderItemDto;
import com.zhy.onlineorder.repository.CartRepository;
import com.zhy.onlineorder.repository.MenuItemRepository;
import com.zhy.onlineorder.repository.OrderItemRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
public class CartService {


    private final CartRepository cartRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;


    public CartService(
            CartRepository cartRepository,
            MenuItemRepository menuItemRepository,
            OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
    }


    @CacheEvict(cacheNames = "cart", key = "#customerId")
    @Transactional
    public void addMenuItemToCart(long customerId, long menuItemId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        MenuItemEntity menuItem = menuItemRepository.findById(menuItemId).get();
        OrderItemEntity orderItem = orderItemRepository.findByCartIdAndMenuItemId(cart.id(), menuItem.id());


        Long orderItemId;
        int quantity;


        if (orderItem == null) {
            orderItemId = null;
            quantity = 1;
        } else {
            orderItemId = orderItem.id();
            quantity = orderItem.quantity() + 1;
        }
        OrderItemEntity newOrderItem = new OrderItemEntity(orderItemId, menuItemId, cart.id(), menuItem.price(), quantity);
        orderItemRepository.save(newOrderItem);
        cartRepository.updateTotalPrice(cart.id(), cart.totalPrice() + menuItem.price());
    }

    @CacheEvict(cacheNames = "cart", key = "#customerId")
    @Transactional
    public void removeMenuItemFromCart(long customerId, long menuItemId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        OrderItemEntity orderItem = orderItemRepository.findByCartIdAndMenuItemId(cart.id(), menuItemId);
        if (orderItem == null) {
            return;
        }

        if (orderItem.quantity() > 1) {
            // quantity > 1: decrease by one (same id, so save() updates the row)
            OrderItemEntity newOrderItem = new OrderItemEntity(
                    orderItem.id(),
                    orderItem.menuItemId(),
                    orderItem.cartId(),
                    orderItem.price(),
                    orderItem.quantity() - 1
            );
            orderItemRepository.save(newOrderItem);
        } else {
            // quantity == 1: remove the row entirely
            orderItemRepository.deleteById(orderItem.id());
        }

        // subtract one item's price from the cart total
        cartRepository.updateTotalPrice(cart.id(), cart.totalPrice() - orderItem.price());
    }


    @Cacheable("cart")
    public CartDto getCart(Long customerId) {
        CartEntity cart = cartRepository.getByCustomerId(customerId);
        List<OrderItemEntity> orderItems = orderItemRepository.getAllByCartIdOrderByIdAsc(cart.id());
        List<OrderItemDto> orderItemDtos = getOrderItemDtos(orderItems);
        return new CartDto(cart, orderItemDtos);
    }


    @CacheEvict(cacheNames = "cart", key = "#customerId")
    @Transactional
    public void clearCart(Long customerId) {
        CartEntity cartEntity = cartRepository.getByCustomerId(customerId);
        orderItemRepository.deleteByCartId(cartEntity.id());
        cartRepository.updateTotalPrice(cartEntity.id(), 0.0);
    }


    private List<OrderItemDto> getOrderItemDtos(List<OrderItemEntity> orderItems) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItemEntity orderItem : orderItems) {
            MenuItemEntity menuItem = menuItemRepository.findById(orderItem.menuItemId()).get();
            OrderItemDto orderItemDto = new OrderItemDto(orderItem, menuItem);
            orderItemDtos.add(orderItemDto);
        }
        return orderItemDtos;
    }
}
