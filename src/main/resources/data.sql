INSERT INTO flights (id, origin, destination, departure, arrival, price, fare_class, carrier)
VALUES ('1', 'London', 'New-York', NOW() + INTERVAL '1' DAY, NOW() + INTERVAL '2' DAY, 500.50, 'ECONOMY', 'Iberia Express'),
       ('2', 'Berlin', 'Paris', NOW() + INTERVAL '2' DAY, NOW() + INTERVAL '3' DAY, 700, 'ECONOMY', 'British Airways'),
       ('3', 'Barselona', 'Rome', NOW() + INTERVAL '3' DAY, NOW() + INTERVAL '4' DAY, 200.25, 'ECONOMY', 'Iberia Express');

INSERT INTO payments (owner, sum, date, status)
VALUES ('Andrii Admin', 700, NOW(), 'NEW'),
       ('Andrii Kravchenko', 500.50, NOW(),'DONE'),
       ('Andrii User', 200.25, NOW(), 'ARCHIVE');

INSERT INTO users (firstname, lastname, email, password, role)
VALUES ('Andrii', 'Admin', 'admin@gmail.com', '$2a$10$Yd44HCeBpiEXsafVDuJ.Je3P3fxK/aEH4v/OEaSYWEL54cgURuzG2', 'ADMIN'),
       ('Andrii', 'User', 'user@gmail.com', '$2a$10$LTziU.WVSx952CRKqmhbqeE8BNiWWCH7i5HTlBHlhfKiEUJKm25FW', 'USER');

INSERT INTO tickets (owner, seat, payment_id, user_id, flight_id)
VALUES ('Andrii Kravchenko', 'F10', 2, 1, 1),
       ('Andrii Kravchenko', 'F11', 1, 1, 2),
       ('Andrii User', 'F12', 3, 2, 3);