SET CLIENT_ENCODING TO 'utf8';

INSERT INTO shoppingcarts(user_id, status)
SELECT  'user_id-' || seq AS user_id,
        'OPEN'  AS status
FROM GENERATE_SERIES(1, 10) seq;


INSERT INTO shopping_cart_elements(id, user_id, game_id, price)
SELECT 'id-' || seq AS id,
        'user_id-' || seq AS user_id,
        'game_id-' || seq AS game_id,
        seq*1.1 AS price
FROM GENERATE_SERIES(1, 10) seq;