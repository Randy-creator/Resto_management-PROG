create DATABASE restaurant;

\c restaurant;

create TABLE IF NOT EXISTS Dish (
    dish_id INT PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE,
    unitPrice INT NOT NULL
);

create type unities as ENUM('G', 'L', 'U');

CREATE TABLE IF NOT EXISTS Ingredient (
    ingredient_id INT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    lastModification TIMESTAMP WITH TIME ZONE,
    unitPrice INT NOT NULL,
    unity unities NOT NULL
);

CREATE TABLE IF NOT EXISTS DishIngredient (
    dish_id INT REFERENCES Dish(dish_id) ON DELETE CASCADE,
    ingredient_id INT REFERENCES Ingredient(ingredient_id) ON DELETE CASCADE,
    quantity FLOAT NOT NULL,
    PRIMARY KEY (dish_id, ingredient_id)
);

CREATE TABLE IF NOT EXISTS Movement (
    move_id INT PRIMARY KEY,
    moveType BOOLEAN,
    quantity FLOAT NOT NULL,
    Unity unities NOT NULL,
    stockDateModification TIMESTAMP WITH TIME ZONE,
    ingredient_id INT REFERENCES Ingredient(ingredient_id) ON DELETE CASCADE
);

create type Status as ENUM('CREATED', 'CONFIRMED', 'IN_PROGRESS', 'DONE', 'SERVED');

CREATE TABLE IF NOT EXISTS "Order" (
    order_id INT PRIMARY KEY,
    order_reference VARCHAR(250)
);


 INSERT INTO "Order" VALUES(1, 'CH001','2025-03-20 10:00') ON CONFLICT DO NOTHING;


CREATE TABLE IF NOT EXISTS OrderDish (
    order_dish_id INT PRIMARY KEY,
    dish_id INT REFERENCES Dish(dish_id) ON DELETE CASCADE,
    order_id INT REFERENCES "Order"(order_id) ON DELETE CASCADE,
    quantity FLOAT NOT NULL
);

INSERT INTO OrderDish VALUES(1, 1, 1, 1) ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS State(
    status_name Status PRIMARY KEY NOT NULL,
    order_id INT REFERENCES "Order"(order_id) ON DELETE CASCADE,
    order_dish_id INT REFERENCES OrderDish(order_dish_id) ON DELETE CASCADE,
    statusModificationDate TIMESTAMP WITH TIME ZONE
);

