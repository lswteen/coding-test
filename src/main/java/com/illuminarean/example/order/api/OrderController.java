package com.illuminarean.example.order.api;

import com.illuminarean.example.order.domain.Order;
import com.illuminarean.example.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders/")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("order")
    public List<Order> getOrderList(Long userId){
        return orderService.findByOrderList(userId);
    }

    @GetMapping("detail/{orderId}")
    public Order getOrderDetail(@PathVariable Long orderId){
        return orderService.findByOrderDetail(orderId);
    }

    @GetMapping("cancel/{orderId}")
    public boolean cancel(@PathVariable Long orderId){
        return orderService.cancelOrder(orderId);
    }


}
