SET CLIENT_ENCODING TO 'utf8';

--achievement
CREATE TABLE achievement_items
(
    item_id         varchar(36) CONSTRAINT pk_achievements PRIMARY KEY,
    game_id         varchar(36) NOT NULL,
    title           varchar(255) NOT NULL
 );

CREATE TABLE user_achievement_items
(
    id              varchar(36) CONSTRAINT pk_user_achievements PRIMARY KEY,
    user_id         varchar(36) NOT NULL,
    game_id         varchar(36) NOT NULL,
    achievement_id  varchar(36) NOT NULL
 );

--game
CREATE TABLE games
(
    id              varchar(36) CONSTRAINT pk_games PRIMARY KEY,
    title           varchar(255) NOT NULL,
    base_price      numeric(10, 6)
 );

CREATE TABLE user_games
(
    id              varchar(36) CONSTRAINT pk_user_games PRIMARY KEY,
    user_id         varchar(36) NOT NULL,
    game_id         varchar(36) NOT NULL
 );

--playhistory
CREATE TABLE play_history
(
    id              varchar(36) CONSTRAINT pk_play_history PRIMARY KEY,
    user_id         varchar(36) NOT NULL,
    game_id         varchar(36) NOT NULL,
    played_time     integer
 );

--PurchaseHistory
CREATE TABLE purchase_history
(
    id              varchar(36) CONSTRAINT pk_purchase_history PRIMARY KEY,
    user_id         varchar(36),
    game_id         varchar(36),
    cost            numeric(10, 6),
    date            timestamp
 );
