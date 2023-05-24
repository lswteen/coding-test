package com.illuminarean.example.order.api.dto;

import com.illuminarean.example.order.domain.Order;
import com.illuminarean.example.review.domain.Review;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class OrderDto {
    private Long id;

    private Long userId;

    private Long productId;

    private Long reviewId;

    private String state;

    private String requestMessage;

    private String rejectMessage;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime completeDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime rejectedDate;

    private Review review;

    //BeanUtils copyProperties 원본 -> 복사대상
    public OrderDto(Order order){
        copyProperties(order, this);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getState() {
        return state;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public LocalDateTime getCompleteDate() {
        return completeDate;
    }

    public LocalDateTime getRejectedDate() {
        return rejectedDate;
    }

    public Review getReview() {
        return review;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public void setCompleteDate(LocalDateTime completeDate) {
        this.completeDate = completeDate;
    }

    public void setRejectedDate(LocalDateTime rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
