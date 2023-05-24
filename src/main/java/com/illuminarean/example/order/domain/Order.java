package com.illuminarean.example.order.domain;

import com.google.common.base.MoreObjects;
import com.illuminarean.example.review.Review;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private Long id;

    private Long userId;

    private Long productId;

    private Long reviewId;

    private String state;

    private String requestMessage;

    private String rejectMessage;

    private LocalDateTime completeDate;

    private LocalDateTime rejectedDate;

    private Review review;

    public Order() {
    }

    private Order(Long id, Long userId, Long productId, Long reviewId, String state, String requestMessage, String rejectMsg, LocalDateTime completeDate, LocalDateTime rejectedDate, Review review) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.reviewId = reviewId;
        this.state = state;
        this.requestMessage = requestMessage;
        this.rejectMessage = rejectMsg;
        this.completeDate = completeDate;
        this.rejectedDate = rejectedDate;
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("userId", userId)
                .add("productId", productId)
                .add("state", state)
                .add("requestMsg", requestMessage)
                .add("rejectMsg", rejectMessage)
                .add("completedAt", completeDate)
                .add("rejectedDate", rejectedDate)
                .toString();
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

    static public class Builder {

        private Long id;
        private Long userId;
        private Long productId;
        private Long reviewId;
        private String state;
        private String requestMessage;
        private String rejectMessage;
        private LocalDateTime completedAt;
        private LocalDateTime rejectedDate;
        private Review review;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder reviewId(Long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder requestMessage(String requestMessage) {
            this.requestMessage = requestMessage;
            return this;
        }

        public Builder rejectMessage(String rejectMessage) {
            this.rejectMessage = rejectMessage;
            return this;
        }

        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder rejectedDate(LocalDateTime rejectedDate) {
            this.rejectedDate = rejectedDate;
            return this;
        }

        public Builder review(Review review) {
            this.review = review;
            return this;
        }

        public Order build() {
            return new Order(
                    this.id,
                    this.userId,
                    this.productId,
                    this.reviewId,
                    this.state,
                    this.requestMessage,
                    this.rejectMessage,
                    this.completedAt,
                    this.rejectedDate,
                    this.review
            );
        }
    }
}
