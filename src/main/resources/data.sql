insert into flights (id, origin, destination, departure, arrival, price, carrier)
values ('1', 'London', 'New-York', current_timestamp() + 1, current_timestamp() + 2, 500.50, 'test1'),
       ('2', 'Berlin', 'Paris', current_timestamp() + 2, current_timestamp() + 3, 700, 'test2'),
       ('3', 'Barselona', 'Rome', current_timestamp() + 3, current_timestamp() + 4, 200.25, 'test1');

insert into payments (owner, sum, date, status)
values ('Andrey1', 700, current_timestamp(), 'NEW'),
       ('Andrey1',500.50, current_timestamp(),'DONE'),
       ('Andrey3',201, current_timestamp(), 'ARCHIVE');

insert into users (name, username, email, password, role)
values ('Andrey1', 'Andreyyyy1', 'andrik1@gmail.com', '12345', 'ADMiN'),
       ('Andrey2', 'Andreyyyy2', 'andrik2@gmail.com', '14345', 'USER'),
       ('Andrey3', 'Andreyyyy3', 'andrik3@gmail.com', '15345', 'USER');

insert into tickets (owner, seat, status, payment_id, user_id, flight_id)
values ('Andrey1', '01Y', 'ECONOMY', 2, 1, 1),
       ('Andrey1', '11J', 'BUSINESS', 1, 1, 2),
       ('Andrey2', '05F', 'FIRST', 3, 2, 3);