INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurants (name)
VALUES ('Pushkin'),
       ('McDonald''s'),
       ('Doner Kebab');

INSERT INTO lunch_menus (restaurant_id, date)
VALUES (1, DATE '2021-08-22'),
       (1, DATE '2021-08-30'),
       (2, DATE '2021-08-22'),
       (2, DATE '2021-08-30'),
       (3, DATE '2021-08-22'),
       (3, DATE '2021-08-30');

INSERT INTO dishes (menu_id, name, price)
VALUES (1, 'Bouillabaisse', 1500),
       (1, 'Poached Egg', 2300),
       (1, 'Ratatouille', 1850),
       (2, 'Nicoise', 1300),
       (2, 'Fish with a Creamy Sauce', 2270.3),
       (2, 'Tart Taten', 2050.21),
       (3, 'Big Mac', 250.11),
       (3, 'Cherry Pie', 170.04),
       (3, 'McFlurry', 90.09),
       (4, 'BigTasty', 320.34),
       (4, 'Nuggets', 120.00),
       (4, 'French Fries', 60),
       (5, 'Shawarma', 100),
       (5, 'Shawerma', 70),
       (5, 'Shavurma', 20),
       (6, 'Chicken in pita bread', 120),
       (6, 'Meat in pita', 100),
       (6, 'Flatbread', 150);

insert into VOTES ("DATE", RESTAURANT_ID, USER_ID)
values (DATE '2021-08-30', 1, 2),
       (DATE '2021-08-22', 3, 1),
       (DATE '2021-08-22', 3, 2);