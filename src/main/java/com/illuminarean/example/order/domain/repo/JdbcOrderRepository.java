package com.illuminarean.example.order.domain.repo;

import com.illuminarean.example.order.domain.Order;
import com.illuminarean.example.review.domain.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Review> reviewMapper;
    private final RowMapper<Order> mapper;

    //생성자에서 jdbcTemplate 을 접근하기 위해서 static사용하다보니
    // 접근에 대한 문제로 인해서 해당 클레스 맴머필드로 변경해서 접근하게 mapper 변경
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.reviewMapper = (rs, rowNum) -> new Review.Builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .productId(rs.getLong("product_id"))
                .content(rs.getString("content"))
                .createDate(rs.getDate("create_date"))
                .build();

        this.mapper = (rs, rowNum) -> {
            Long userId = rs.getLong("user_id");
            Long productId = rs.getLong("product_id");

            // Get the review for this order
            Review review = null;

            List<Review> reviews = this.jdbcTemplate.query(
                    "SELECT * FROM `review` WHERE user_id = ? AND product_id = ?",
                    this.reviewMapper,
                    userId,
                    productId
            );
            //여러개의 리뷰중  첫번째만 가져오도록 작업 리스트로 가져와야할때는 Review DTO수정필요
            review = reviews.isEmpty() ? null : reviews.get(0);

            return new Order.Builder()
                    .id(rs.getLong("id"))
                    .userId(userId)
                    .productId(productId)
                    .reviewId(rs.getLong("review_id"))
                    .state(rs.getString("state"))
                    .requestMessage(rs.getString("request_msg"))
                    .rejectMessage(rs.getString("reject_msg"))
                    .completedAt(rs.getTimestamp("complete_date").toLocalDateTime())
                    .rejectedDate(rs.getTimestamp("rejected_date").toLocalDateTime())
                    .review(review)
                    .build();
        };
    }

    @Override
    public Optional<List<Order>> findByOrderList(Long userId) {
        List<Order> orders = jdbcTemplate.query(
                "SELECT * FROM `order` WHERE user_id = ?",
                mapper,
                userId
        );
        return Optional.ofNullable(orders);
    }

    @Override
    public Optional<Order> findByOrderDetail(Order order) {
        Order orderDetail = jdbcTemplate.queryForObject(
                "SELECT * FROM `order` WHERE id = ?",
                mapper,
                order.getId()
        );
        return Optional.ofNullable(orderDetail);
    }

    @Override
    public boolean cancelOrder(Order order) {
        int rowsUpdated = jdbcTemplate.update(
                "UPDATE `order` SET state = 'REJECTED' WHERE id = ? AND state NOT IN ('SHIPPING', 'COMPLETED')",
                order.getId()
        );

        return rowsUpdated > 0;
    }
}