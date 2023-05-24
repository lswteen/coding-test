package com.illuminarean.example.order.service;

import com.illuminarean.example.error.NotFoundException;
import com.illuminarean.example.order.domain.Order;
import com.illuminarean.example.order.domain.repo.OrderRepository;
import com.illuminarean.example.review.domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문 정보 조회")
    void findByOrderList() {
        // Given
        Order order = new Order(); // Add necessary fields
        when(orderRepository.findByOrderList(anyLong()))
                .thenReturn(Optional.of(Arrays.asList(order)));

        // When
        List<Order> result = orderService.findByOrderList(1L);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(order, result.get(0));
    }

    @Test
    @DisplayName("주문 정보 상세")
    void findByOrderDetail() {
        // Given
        Order order = new Order(); // Add necessary fields
        when(orderRepository.findByOrderDetail(anyLong()))
                .thenReturn(Optional.of(order));

        // When
        Order result = orderService.findByOrderDetail(1L);

        // Then
        assertEquals(order, result);
    }


    @Test
    @DisplayName("주문상세 Order Review 구조")
    void findByOrderDetailToValue() {
        // Given
        Review review = new Review.Builder()
                .id(1L)
                .userId(1L)
                .productId(1L)
                .content("Review content")
                .createDate(new Date())
                .build();
        Order order = new Order.Builder()
                .id(1L)
                .userId(1L)
                .productId(1L)
                .reviewId(1L)
                .state("COMPLETED")
                .requestMessage("Request message")
                .rejectMessage("Reject message")
                .completedAt(LocalDateTime.now())
                .rejectedDate(LocalDateTime.now())
                .review(review)
                .build();

        when(orderRepository.findByOrderDetail(anyLong()))
                .thenReturn(Optional.of(order));

        // When
        Order result = orderService.findByOrderDetail(1L);

        // Then
        assertEquals(order, result);
        assertNotNull(result.getReview());
        assertEquals(review, result.getReview());
        assertEquals(order.getReview().getContent(), result.getReview().getContent());
        assertEquals(order.getReview().getCreateDate(), result.getReview().getCreateDate());
    }

    @Test
    @DisplayName("주문 상세 데이터 없을경우")
    void findByOrderDetailNotFound() {
        // Given
        when(orderRepository.findByOrderDetail(anyLong()))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> orderService.findByOrderDetail(1L));
    }

    @Test
    @DisplayName("주문 취소 성공")
    void cancelOrder() {
        // Given
        when(orderRepository.cancelOrder(anyLong()))
                .thenReturn(true);

        // When
        boolean result = orderService.cancelOrder(1L);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("주문 취소 실패")
    void cancelOrderFailure() {
        // Given
        when(orderRepository.cancelOrder(anyLong()))
                .thenReturn(false);

        // When
        boolean result = orderService.cancelOrder(1L);

        // Then
        assertFalse(result);
    }

}