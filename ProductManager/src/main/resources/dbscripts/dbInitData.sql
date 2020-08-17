SET CLIENT_ENCODING TO 'utf8';

INSERT INTO games(id, title, base_price)
SELECT 'game-id-' || seq AS id,
        'title-' || seq AS title,
        seq*1.1  || seq AS base_price
FROM GENERATE_SERIES(1, 10) seq;
