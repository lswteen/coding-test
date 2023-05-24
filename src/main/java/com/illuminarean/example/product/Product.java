package com.illuminarean.example.product;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

    private Long id;

    private String name;

    private String details;

    private int reviewCount;

    private LocalDateTime createDate;

    public Product() {}

    public Product(Long id, String name, String details, int reviewCount, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.reviewCount = reviewCount;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("details", details)
                .add("reviewCount", reviewCount)
                .add("createDate", createDate)
                .toString();
    }

    static public class Builder {
        private Long id;
        private String name;
        private String details;
        private int reviewCount;
        private LocalDateTime createDate;

        public Builder() {/*empty*/}

        public Builder(Product product) {
            this.id = product.id;
            this.name = product.name;
            this.details = product.details;
            this.reviewCount = product.reviewCount;
            this.createDate = product.createDate;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder reviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public Builder createDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Product build() {
            return new Product(
                    id,
                    name,
                    details,
                    reviewCount,
                    createDate
            );
        }
    }

}
