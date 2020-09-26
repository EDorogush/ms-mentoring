SET CLIENT_ENCODING TO 'utf8';

CREATE TABLE invoices
(
    id                 VARCHAR(36)
        CONSTRAINT pk_invoices
            PRIMARY KEY,
    user_id            VARCHAR(36),
    bill               NUMERIC(10, 6),
    status             VARCHAR(20),
    last_update        TIMESTAMP
);

