package com.illuminarean.example.order.domain.repo;

import com.illuminarean.example.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository{
    Optional<List<Order>> findByOrderList(Long userId);
    Optional<Order> findByOrderDetail(Long orderId);
    boolean cancelOrder(Long orderId);
}
