SET CLIENT_ENCODING TO 'utf8';

CREATE TABLE games
(
    id              varchar(36)
                    CONSTRAINT pk_games
                    PRIMARY KEY,
    title           varchar(255),
    base_price      numeric(10, 6)
 );

ALTER TABLE games
    OWNER TO gameuser;



PurchaseHistoryItem
CREATE TABLE purchase_history
(
    id              varchar(36)
                    CONSTRAINT pk_games
                    PRIMARY KEY,
    user_id         varchar(36),
    game_id         varchar(36),
    cost            numeric(10, 6),
    date            timestamp
 );

ALTER TABLE purchase_history
    OWNER TO gameuser;
