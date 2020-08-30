SET CLIENT_ENCODING TO 'utf8';

INSERT INTO achievement_items(item_id, game_id, title)
VALUES ('item_id-1', 'game_id-1', 'achievement-1'),
       ('item_id-2', 'game_id-2', 'achievement-2'),
       ('item_id-3', 'game_id-3', 'achievement-3'),
       ('item_id-4', 'game_id-1', 'achievement-4'),
       ('item_id-5', 'game_id-2', 'achievement-5'),
       ('item_id-6', 'game_id-3', 'achievement-6'),
       ('item_id-7', 'game_id-4', 'achievement-7'),
       ('item_id-8', 'game_id-5', 'achievement-8'),
       ('item_id-9', 'game_id-3', 'achievement-9'),
       ('item_id-10', 'game_id-4', 'achievement-10'),
       ('item_id-11', 'game_id-5', 'achievement-11');


INSERT INTO user_achievement_items(id, user_id, game_id, achievement_id)
VALUES ('user-achvmt-1', 'user_id-9', 'game_id-1', 'item_id-1'),
       ('user-achvmt-2', 'user_id-1', 'game_id-2', 'item_id-2'),
       ('user-achvmt-3', 'user_id-9', 'game_id-3', 'item_id-3'),
       ('user-achvmt-4', 'user_id-1', 'game_id-1', 'item_id-4');


INSERT INTO games(id, title, base_price)
SELECT 'game_id-' || seq AS id,
        'title-' || seq AS title,
        seq*1.1 AS base_price
FROM GENERATE_SERIES(1, 10) seq;


INSERT INTO user_games(id, user_id, game_id)
VALUES  ('user_games_id-1', 'user_id-9', 'game_id-1'),
        ('user_games_id-2', 'user_id-9', 'game_id-2'),
        ('user_games_id-3', 'user_id-9', 'game_id-3'),
        ('user_games_id-4', 'user_id-1', 'game_id-1'),
        ('user_games_id-5', 'user_id-1', 'game_id-2'),
        ('user_games_id-6', 'user_id-2', 'game_id-1');


INSERT INTO play_history(id, user_id, game_id, played_time)
VALUES ('played_id-1', 'user_id-9', 'game_id-1', 2),
       ('played_id-2', 'user_id-9', 'game_id-2', 10);


INSERT INTO purchase_history(id, user_id, game_id, cost, date)
VALUES ('purchase_id-1', 'user_id-9', 'game_id-1', 1.223, timestamp '2020-8-19 10:00:00'),
       ('purchase_id-2', 'user_id-9', 'game_id-2', 2.223, timestamp '2020-8-19 10:00:00'),
       ('purchase_id-3', 'user_id-9', 'game_id-3', 4.223, timestamp '2020-8-19 10:00:00'),
       ('purchase_id-4', 'user_id-1', 'game_id-1', 2.223, timestamp '2020-8-19 10:00:00'),
       ('purchase_id-5', 'user_id-1', 'game_id-2', 4.223, timestamp '2020-8-19 10:00:00'),
       ('purchase_id-6', 'user_id-2', 'game_id-1', 2.223, timestamp '2020-8-19 10:00:00');

