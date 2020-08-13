SET CLIENT_ENCODING TO 'utf8';

CREATE TABLE shoppingcarts
(
    id                    varchar(36)
        CONSTRAINT pk_shoppingcarts
            PRIMARY KEY,
    user_id               varchar(36),
    game_id               varchar(36)
);

ALTER TABLE shoppingcarts
    OWNER TO shoppingcartuser;

