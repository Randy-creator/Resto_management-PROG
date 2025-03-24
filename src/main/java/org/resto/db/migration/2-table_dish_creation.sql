create TABLE IF NOT EXISTS Dish (
    dish_id INT PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE,
    unitPrice INT NOT NULL
);