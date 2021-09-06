DROP TABLE IF EXISTS user_roles, users, restaurants, lunch_menus, dishes, votes;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    enabled    BOOL      DEFAULT true,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT users_unique_email_idx UNIQUE (email)
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_unique_user_id_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    name VARCHAR(255),
    CONSTRAINT restaurants_unique_name_idx UNIQUE (name)
);

CREATE TABLE lunch_menus
(
    id            INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    restaurant_id INTEGER               NOT NULL,
    date          DATE    DEFAULT now() NOT NULL,
    CONSTRAINT lunch_menus_unique_restaurant_id_date_idx UNIQUE (restaurant_id, date),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE dishes
(
    id      INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    menu_id INTEGER        NOT NULL,
    name    VARCHAR(255)   NOT NULL,
    price   DECIMAL(10, 2) NOT NULL,
    CONSTRAINT dishes_unique_menu_id_description_idx UNIQUE (menu_id, name),
    FOREIGN KEY (menu_id) REFERENCES lunch_menus (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id            INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    restaurant_id INTEGER               NOT NULL,
    user_id       INTEGER               NOT NULL,
    date            DATE    DEFAULT now() NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT votes_unique_user_id_restaurant_id_date_idx UNIQUE (user_id, restaurant_id, date)
);