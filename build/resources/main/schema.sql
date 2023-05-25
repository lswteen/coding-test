DROP TABLE IF EXISTS `order` CASCADE;
DROP TABLE IF EXISTS `review` CASCADE;
DROP TABLE IF EXISTS `review_reply` CASCADE;
DROP TABLE IF EXISTS `review_photo` CASCADE;
DROP TABLE IF EXISTS `product` CASCADE;
DROP TABLE IF EXISTS `user` CASCADE;
DROP TABLE IF EXISTS `mileage` CASCADE;

CREATE TABLE `user`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT, --사용자 PK
    `name`            varchar(10) NOT NULL,                --사용자명
    `email`           varchar(50) NOT NULL,                --로그인 이메일
    `password`        varchar(80) NOT NULL,                --로그인 비밀번호
    `login_count`     int         NOT NULL DEFAULT 0,      --로그인 횟수. 로그인시 마다 1 증가
    `last_login_date` datetime             DEFAULT NULL,   --최종 로그인 일자
    `create_date`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `modified_date`   datetime,
    PRIMARY KEY (`id`),
    CONSTRAINT unq_user_email UNIQUE (`email`)
);

CREATE TABLE `product`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT, --상품 PK
    `name`         varchar(50) NOT NULL,                --상품명
    `details`      varchar(1000)        DEFAULT NULL,   --상품설명
    `review_count` int         NOT NULL DEFAULT 0,      --리뷰 갯수. 리뷰가 새로 작성되면 1 증가
    `create_date`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id)
);

CREATE TABLE `review`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT, --리뷰 PK
    `user_id`     bigint        NOT NULL,                --리뷰 작성자 PK (user 테이블 참조)
    `product_id`  bigint        NOT NULL,                --리뷰 상품 PK (product 테이블 참조)
    `content`     varchar(1000) NOT NULL,                --리뷰 내용
    `state`       varchar(100)  DEFAULT 'PUBLIC' NOT NULL, --공개 여부 'PUBLIC','PRIVATE'
    `likes`       bigint        DEFAULT 0 NOT NULL,      --좋아요 개수
    `create_date` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id),
    CONSTRAINT fk_review_to_user FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_review_to_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE `review_reply`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT, --리뷰 답글 PK
    `review_id`   bigint        NOT NULL,                --리뷰 PK (review 테이블 참조)
    `user_id`     bigint        NOT NULL,                --답글 작성자 PK (user 테이블 참조)
    `content`     varchar(1000) NOT NULL,                --답글 내용
    `create_date` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id),
    CONSTRAINT fk_review_reply_to_review FOREIGN KEY (review_id) REFERENCES review (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_review_reply_to_user FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `review_photo`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT, --리뷰 사진 PK
    `review_id`   bigint        NOT NULL,                --리뷰 PK (review 테이블 참조)
    `photo_url`   varchar(1000) NOT NULL,                --사진 URL
    `create_date` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id),
    CONSTRAINT fk_review_photo_to_review FOREIGN KEY (review_id) REFERENCES review (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `mileage`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT, -- 마일리지 PK
    `user_id`     bigint        NOT NULL,                -- 마일리지 사용자 PK (user 테이블 참조)
    `order_id`    bigint        NOT NULL,                -- 주문 PK (`order` 테이블 참조)
    `review_id`   bigint        NOT NULL,                -- 리뷰 PK (review 테이블 참조)
    `amount`      int           NOT NULL,                -- 마일리지 양 (100 또는 300)
    `create_date` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id),
    CONSTRAINT fk_mileage_to_user FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_mileage_to_review FOREIGN KEY (review_id) REFERENCES review (id) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE `order`
(
    `id`             bigint   NOT NULL AUTO_INCREMENT,               --주문 PK
    `user_id`        bigint   NOT NULL,                              --주문자 PK (user 테이블 참조)
    `product_id`     bigint   NOT NULL,                              --주문상품 PK (product 테이블 참조)
    `review_id`      bigint            DEFAULT NULL,                 --주문에 대한 리뷰 PK (review 테이블 참조)
    `state`          varchar(100)      DEFAULT 'REQUESTED' NOT NULL, --주문상태 'REQUESTED','ACCEPTED','SHIPPING','COMPLETED','REJECTED'
    `request_msg`    varchar(1000)     DEFAULT NULL,                 --주문 요청 메시지
    `reject_msg`     varchar(1000)     DEFAULT NULL,                 --주문 거절 메시지
    `complete_date` datetime          DEFAULT NULL,                 --주문 완료 처리 일자
    `rejected_date`  datetime          DEFAULT NULL,                 -- 주문 거절일자
    `create_date`    datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id),
    CONSTRAINT unq_review_id UNIQUE (review_id),
    CONSTRAINT fk_order_to_user FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_order_to_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_order_to_review FOREIGN KEY (review_id) REFERENCES review (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);