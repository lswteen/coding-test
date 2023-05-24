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

    //인증된 사용자가 주문목록을 조회한다.
    public List<Order> findByOrderList(Long userId){
        return orderRepository.findByOrderList(userId)
                .orElse(Collections.emptyList());
    }
    //인증된 사용자가 본인의 주문 상세 내용을 조회한다.
    public Order findByOrderDetail(Long orderId){
        return orderRepository.findByOrderDetail(orderId)
                .orElseThrow(()-> new NotFoundException("Cloud nof found orderId for" + orderId));
    }

    //인증된 사용자가 본인의 주문을 취소한다. 주문상태 : 'SHIPPING', 'COMPLETED' 취소 불가
    public boolean cancelOrder(Long orderId){
        return orderRepository.cancelOrder(orderId);
    }

}
