package com.illuminarean.example.order.domain.repo;

import com.illuminarean.example.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository{
    Optional<List<Order>> findByOrderList(Order order);
    Optional<Order> findByOrderDetail(Order order);
    boolean cancelOrder(Order order);
}
