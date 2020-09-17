SET CLIENT_ENCODING TO 'utf8';

CREATE TABLE shoppingcarts
(
    user_id     varchar(36) CONSTRAINT pk_shoppingcarts PRIMARY KEY,
    status      varchar(255)
);


CREATE TABLE shopping_cart_elements
(
    id          varchar(36) CONSTRAINT pk_shoppingcartelements PRIMARY KEY,
    user_id     varchar(36),
    game_id     varchar(36),
    price       numeric(10, 6),
    FOREIGN KEY (user_id) REFERENCES shoppingcarts(user_id)
);


