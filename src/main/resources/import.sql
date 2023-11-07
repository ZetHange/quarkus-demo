-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;
insert into person(id, username, email, password, role, registeredat) values (1, 'zethange', 'zethange@yandex.ru', '1234', 'ADMIN', '12.04.2022');
insert into person(id, username, email, password, role, registeredat) values (2, 'pomidor', 'pomidor@yandex.ru', '12345', 'USER', '12.04.2022');