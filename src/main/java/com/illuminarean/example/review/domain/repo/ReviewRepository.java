package com.illuminarean.example.review.domain.repo;

import com.illuminarean.example.review.domain.Review;
import com.illuminarean.example.review.domain.ReviewPhoto;
import com.illuminarean.example.review.domain.ReviewReply;

import java.util.List;

public interface ReviewRepository{
    // 리뷰 공개 상태 설정
    void setReviewState(Long reviewId, String state);

    // 리뷰 작성 시, 리워드를 받는 기능
    // 리뷰 작성 후, 리턴값으로 보상된 마일리지를 받을 수 있도록 했습니다.
    int writeReview(Review review);

    // 리뷰에 좋아요를 추가하는 기능
    // 리턴값은 좋아요가 추가된 후의 총 좋아요 개수입니다.
    int addLike(Long reviewId);

    // 리스트 형태로 리뷰를 조회하는 메서드
    List<Review> findReviews(Long userId, Long productId, String state);

    // 리뷰 답글 작성 메소드
    void writeReply(ReviewReply reply);

    // 리뷰 답글 조회 메소드
    List<ReviewReply> findReplies(Long reviewId);

    // 리뷰 사진 추가 메소드
    void addPhoto(ReviewPhoto photo);

    // 리뷰 사진 조회 메소드
    List<ReviewPhoto> findPhotos(Long reviewId);

}
