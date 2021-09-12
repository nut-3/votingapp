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

INSERT INTO lunch_menus (restaurant_id, curr_date)
VALUES (1, DATE '2021-08-22'),
       (1, DATE '2021-08-30'),
       (2, DATE '2021-08-22'),
       (2, DATE '2021-08-30'),
       (3, DATE '2021-08-22'),
       (3, DATE '2021-08-30'),
       (1, CURRENT_DATE),
       (2, CURRENT_DATE),
       (3, CURRENT_DATE);

INSERT INTO dishes (menu_id, name, price)
VALUES (1, 'Bouillabaisse', 1500),
       (1, 'Poached Egg', 2300),
       (1, 'Ratatouille', 1850),
       (2, 'Nicoise', 1300),
       (2, 'Fish with a Creamy Sauce', 2270),
       (2, 'Tart Taten', 2050),
       (3, 'Big Mac', 250),
       (3, 'Cherry Pie', 170),
       (3, 'McFlurry', 90),
       (4, 'BigTasty', 320),
       (4, 'Nuggets', 120),
       (4, 'French Fries', 60),
       (5, 'Shawarma', 100),
       (5, 'Shawerma', 70),
       (5, 'Shavurma', 20),
       (6, 'Chicken in pita bread', 120),
       (6, 'Meat in pita', 100),
       (6, 'Flatbread', 150),
       -- Today dishes --
       (7, 'Duck breast', 5000),
       (7, 'Ricotta gnocchi', 1700),
       (7, 'Rostbif', 6200),
       (8, 'French fries', 99),
       (8, 'Cheeseburger', 120),
       (8, 'Shrimp roll', 150),
       (9, 'Kharcho', 120),
       (9, 'Khachapuri', 100),
       (9, 'Veg salad', 150);

INSERT INTO votes (curr_date, restaurant_id, user_id)
VALUES (DATE '2021-08-30', 1, 2),
       (DATE '2021-08-22', 3, 1),
       (DATE '2021-08-22', 3, 2);