SET CLIENT_ENCODING TO 'utf8';

INSERT INTO shoppingcarts(id, user_id, game_id)
SELECT 'id-' || seq AS id,
        'user_id-' || seq AS user_id,
        'game_id-' || seq AS game_id
FROM GENERATE_SERIES(1, 10) seq;
