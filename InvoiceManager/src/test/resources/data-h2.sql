INSERT INTO invoices(id, user_id, bill, status, last_update)
VALUES ('id-1', 'user_id-1', 1.1, 'WAIT',timestamp '2020-9-14 04:00:00'),
       ('id-2', 'user_id-2', 2.1, 'OK',timestamp '2020-8-14 05:00:00'),
       ('id-3', 'user_id-3', 3.1, 'PROCESS',timestamp '2020-8-13 20:00:00'),
       ('id-4', 'user_id-4', 4.1, 'REJECT',timestamp '2020-8-19 20:00:00'),
       ('id-5', 'user_id-5', 5.1, 'WAIT',timestamp '2020-8-20 20:00:00'),
       ('id-6', 'user_id-6', 6.1, 'WAIT',timestamp '2020-8-20 20:00:00');
