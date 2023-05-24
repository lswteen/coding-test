-- User 데이터 생성
INSERT INTO user(id, email, password, name)
VALUES (1, 'tester@gmail.com', '$2a$10$mzF7/rMylsnxxwNcTsJTEOFhh1iaHv3xVox.vpf6JQybEhE4jDZI.', 'tester');


-- Product 데이터 생성
INSERT INTO product(id, name, details, review_count)
VALUES (1, 'Product 1', 'test product', 0);
INSERT INTO product(id, name, details, review_count)
VALUES (2, 'Product 2', 'test product 2', 1);
INSERT INTO product(id, name, details, review_count)
VALUES (3, 'Product 3', 'test product 3', 0);

-- Review 데이터 생성
INSERT INTO review(id, user_id, product_id, content)
VALUES (1, 1, 2, 'good!!!!');

-- Order 데이터 생성
INSERT INTO `order`(id, user_id, product_id, review_id, state, request_msg, reject_msg, complete_date, rejected_date)
VALUES (null, 1, 1, null, 'REQUESTED', null, null, null, null);
INSERT INTO `order`(id, user_id, product_id, review_id, state, request_msg, reject_msg, complete_date, rejected_date)
VALUES (null, 1, 1, null, 'ACCEPTED', null, null, null, null);
INSERT INTO `order`(id, user_id, product_id, review_id, state, request_msg, reject_msg, complete_date, rejected_date)
VALUES (null, 1, 2, null, 'SHIPPING', null, null, null, null);
INSERT INTO `order`(id, user_id, product_id, review_id, state, request_msg, reject_msg, complete_date, rejected_date)
VALUES (null, 1, 2, 1, 'COMPLETED', 'test request', null, '2021-01-24 12:10:30', null);
INSERT INTO `order`(id, user_id, product_id, review_id, state, request_msg, reject_msg, complete_date, rejected_date)
VALUES (null, 1, 3, null, 'COMPLETED', null, null, '2021-01-24 10:30:10', null);
INSERT INTO `order`(id, user_id, product_id, review_id, state, request_msg, reject_msg, complete_date, rejected_date)
VALUES (null, 1, 3, null, 'REJECTED', null, 'reject message', null, '2021-01-24 18:30:00');
INSERT INTO `order`(id, user_id, product_id, review_id, state, request_msg, reject_msg, complete_date, rejected_date)
VALUES (null, 1, 3, null, 'REQUESTED', null, null, null, null);

