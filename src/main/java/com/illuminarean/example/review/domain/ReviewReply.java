package com.illuminarean.example.review.domain;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

public class ReviewReply {

    private Long id;

    private Long reviewId;

    private Long userId;

    private String content;

    private Date createDate;

    public ReviewReply() {}

    private ReviewReply(Long reviewId, Long userId, String content) {
        this(null, reviewId, userId, content, new Date());
    }

    private ReviewReply(Long id, Long reviewId, Long userId, String content, Date createDate) {
        this.id = id;
        this.reviewId = reviewId;
        this.userId = userId;
        this.content = content;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getUserId() {
        return userId;
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
        ReviewReply that = (ReviewReply) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("reviewId", reviewId)
                .add("userId", userId)
                .add("content", content)
                .add("createDate", createDate)
                .toString();
    }

    public static class Builder {

        private Long id;

        private Long reviewId;

        private Long userId;

        private String content;

        private Date createDate;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder reviewId(Long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
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

        public ReviewReply build() {
            return new ReviewReply(
                    this.id,
                    this.reviewId,
                    this.userId,
                    this.content,
                    this.createDate
            );
        }
    }
}
