INSERT INTO shoppingcarts(user_id, status)
VALUES ('user_id-1', 'OPEN'),
       ('user_id-2', 'APPROVED'),
       ('user_id-3', 'REJECTED'),
       ('user_id-4', 'STUCK'),
       ('user_id-5', 'INVOICE');

INSERT INTO shopping_cart_elements(id, user_id, game_id, price)
VALUES ('id-1', 'user_id-2', 'game_id-1', 1.1),
       ('id-2', 'user_id-2', 'game_id-2', 2.2),
       ('id-3', 'user_id-3', 'game_id-1', 1.1),
       ('id-4', 'user_id-3', 'game_id-2', 2.2),
       ('id-5', 'user_id-4', 'game_id-1', 1.1),
       ('id-6', 'user_id-5', 'game_id-1', 2.2),
       ('id-7', 'user_id-5', 'game_id-2', 1.1),
       ('id-8', 'user_id-5', 'game_id-3', 2.2);