SET CLIENT_ENCODING TO 'utf8';

INSERT INTO invoices(id, user_id, bill, status)
SELECT 'id-' || seq AS id,
        'user_id-' || seq AS user_id,
        seq*1.1 AS bill,
        1   as status
FROM GENERATE_SERIES(1, 10) seq;
