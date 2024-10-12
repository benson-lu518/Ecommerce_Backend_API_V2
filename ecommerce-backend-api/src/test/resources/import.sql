

INSERT INTO user (email, password, created_date, last_modified_date) VALUES ('user1@gmail.com', '202cb962ac59075b964b07152d234b70', '2022-06-30 10:30:00', '2022-06-30 10:30:00');
INSERT INTO user (email, password, created_date, last_modified_date) VALUES ('user2@gmail.com', '202cb962ac59075b964b07152d234b70', '2022-06-30 10:40:00', '2022-06-30 10:40:00');

-- product
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Audi', 'CAR', 'https://test', 1000, 100, 'This is a car！', '2022-03-19 17:00:00', '2022-03-22 18:00:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Apple', 'FOOD', 'https://test', 500, 100, 'This is an apple！', '2022-03-19 18:30:00', '2022-03-19 18:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Economic', 'E_BOOK', 'https://test', 200, 100, null, '2022-03-20 09:00:00', '2022-03-24 15:00:00');
--
---- order, order_item
--INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (1, 500690, '2022-06-30 11:10:00', '2022-06-30 11:10:00');
--INSERT INTO order_item ( product_id, quantity, amount) VALUES (1,  3, 90);
--INSERT INTO order_item ( product_id, quantity, amount) VALUES (1,  2, 600);
--INSERT INTO order_item ( product_id, quantity, amount) VALUES (1,  1, 500000);
--
--INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (1, 100000, '2022-06-30 12:03:00', '2022-06-30 12:03:00');
--INSERT INTO order_item ( product_id, quantity, amount) VALUES (2, 1, 100000);
