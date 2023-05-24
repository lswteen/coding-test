package com.illuminarean.example.review.domain;


import com.google.common.base.MoreObjects;

import java.util.Objects;

public class ReviewPhoto {

    private Long id;

    private Long reviewId;

    private String photoUrl;

    public ReviewPhoto() {}

    private ReviewPhoto(Long reviewId, String photoUrl) {
        this(null, reviewId, photoUrl);
    }

    private ReviewPhoto(Long id, Long reviewId, String photoUrl) {
        this.id = id;
        this.reviewId = reviewId;
        this.photoUrl = photoUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewPhoto that = (ReviewPhoto) o;
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
                .add("photoUrl", photoUrl)
                .toString();
    }

    public static class Builder {

        private Long id;

        private Long reviewId;

        private String photoUrl;

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

        public Builder photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public ReviewPhoto build() {
            return new ReviewPhoto(
                    this.id,
                    this.reviewId,
                    this.photoUrl
            );
        }
    }
}