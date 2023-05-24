package com.illuminarean.example.order.service;

import com.illuminarean.example.error.NotFoundException;
import com.illuminarean.example.order.domain.Order;
import com.illuminarean.example.order.domain.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findByOrderList(Long userId){
        return orderRepository.findByOrderList(userId)
                .orElse(Collections.emptyList());
    }
    public Order findByOrderDetail(Long orderId){
        return orderRepository.findByOrderDetail(orderId)
                .orElseThrow(()-> new NotFoundException("Cloud nof found order for" + orderId));
    }

    //주문상태 : 'SHIPPING', 'COMPLETED' 취소 불가
    public boolean cancelOrder(Long orderId){
        return orderRepository.cancelOrder(orderId);
    }

}
