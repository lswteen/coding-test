package com.illuminarean.example.review;

import com.google.common.base.MoreObjects;
import com.illuminarean.example.order.domain.Order;

import java.util.Date;
import java.util.Objects;

public class Review {

    private Long id;

    private Long userId;

    private Long productId;

    private String content;

    private Date createDate;

    public Review() {}

    private Review(Long userId, Long productId, String content) {
        this(null, userId, productId, content, new Date());
    }

    private Review(Long id, Long userId, Long productId, String content, Date createDate) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.content = content;
        this.createDate = createDate;
    }

    public Review(Order order, String content) {
        this(order.getUserId(), order.getProductId(), content);
    }

    public Review(Long id, Long productId, String content, Date createDate) {
        this.id = id;
        this.productId = productId;
        this.content = content;
        this.createDate = createDate;
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

    public String getContent() {
        return content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
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
                .add("content", content)
                .add("createDate", createDate)
                .toString();
    }

    static public class Builder {

        private Long id;

        private Long userId;

        private Long productId;

        private String content;

        private Date createDate;

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

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }

        public Review build() {
            return new Review(
                    this.id,
                    this.userId,
                    this.productId,
                    this.content,
                    this.createDate
            );
        }
    }
}
