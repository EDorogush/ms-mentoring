SET CLIENT_ENCODING TO 'utf8';

INSERT INTO invoices(id, user_id, bill, status, last_update)
SELECT 'id-' || seq AS id,
        'user_id-' || seq AS user_id,
        seq*1.1 AS bill,
        'OK'   as status,
        timestamp '2020-8-19 10:00:00' +
               random() * (timestamp '2020-8-19 20:00:00' -
                           timestamp '2020-8-19 10:00:00')
        AS last_update

FROM GENERATE_SERIES(1, 10) seq;
