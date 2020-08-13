SET CLIENT_ENCODING TO 'utf8';

CREATE TABLE invoices
(
    id                    varchar(36)
        CONSTRAINT pk_invoices
            PRIMARY KEY,
    user_id               varchar(36),
    bill               NUMERIC(10, 6),
    status             integer
);

ALTER TABLE invoices
    OWNER TO invoicedbuser;

