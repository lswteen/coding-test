package com.illuminarean.example.review.domain.repo;

import com.illuminarean.example.review.domain.Review;
import com.illuminarean.example.review.domain.ReviewPhoto;
import com.illuminarean.example.review.domain.ReviewReply;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class jdbcReviewRepository implements ReviewRepository{
    private final JdbcTemplate jdbcTemplate;

    public jdbcReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setReviewState(Long reviewId, String state) {
        String sql = "UPDATE review SET state = ? WHERE id = ?";
        jdbcTemplate.update(sql, state, reviewId);
    }

    @Override
    public int writeReview(Review review) {
        String sql = "INSERT INTO review (user_id, product_id, content, state, likes, create_date) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, review.getUserId());
                    ps.setLong(2, review.getProductId());
                    ps.setString(3, review.getContent());
                    ps.setString(4, review.getState());
                    ps.setLong(5, review.getLikes());
                    ps.setTimestamp(6, new java.sql.Timestamp(review.getCreateDate().getTime()));
                    return ps;
                },
                keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public int addLike(Long reviewId) {
        String sql = "UPDATE review SET likes = likes + 1 WHERE id = ?";
        return jdbcTemplate.update(sql, reviewId);
    }

    @Override
    public List<Review> findReviews(Long userId, Long productId, String state) {
        StringBuilder sql = new StringBuilder("SELECT * FROM review WHERE 1=1");
        List<Object> args = new ArrayList<>();

        if (userId != null) {
            sql.append(" AND user_id = ?");
            args.add(userId);
        }
        if (productId != null) {
            sql.append(" AND product_id = ?");
            args.add(productId);
        }
        if (state != null) {
            sql.append(" AND state = ?");
            args.add(state);
        }

        return jdbcTemplate.query(
                sql.toString(),
                args.toArray(),
                (rs, rowNum) -> new Review.Builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .productId(rs.getLong("product_id"))
                        .content(rs.getString("content"))
                        .state(rs.getString("state"))
                        .likes(rs.getLong("likes"))
                        .createDate(rs.getTimestamp("create_date"))
                        .build()
        );
    }

    @Override
    public void writeReply(ReviewReply reply) {
        String sql = "INSERT INTO review_reply (review_id, user_id, content) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reply.getReviewId(), reply.getUserId(), reply.getContent());
    }

    @Override
    public List<ReviewReply> findReplies(Long reviewId) {
        String sql = "SELECT * FROM review_reply WHERE review_id = ?";
        return jdbcTemplate.query(sql, new Object[]{reviewId}, (rs, rowNum) -> new ReviewReply.Builder()
                .id(rs.getLong("id"))
                .reviewId(rs.getLong("review_id"))
                .userId(rs.getLong("user_id"))
                .content(rs.getString("content"))
                .createDate(rs.getTimestamp("create_date"))
                .build()
        );
    }

    @Override
    public void addPhoto(ReviewPhoto photo) {
        String sql = "INSERT INTO review_photo (review_id, photo_url) VALUES (?, ?)";
        jdbcTemplate.update(sql, photo.getReviewId(), photo.getPhotoUrl());
    }

    @Override
    public List<ReviewPhoto> findPhotos(Long reviewId) {
        String sql = "SELECT * FROM review_photo WHERE review_id = ?";
        return jdbcTemplate.query(sql, new Object[]{reviewId}, (rs, rowNum) -> new ReviewPhoto.Builder()
                .id(rs.getLong("id"))
                .reviewId(rs.getLong("review_id"))
                .photoUrl(rs.getString("photo_url"))
                .build()
        );
    }
}
