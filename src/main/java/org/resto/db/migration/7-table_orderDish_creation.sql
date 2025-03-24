CREATE TABLE IF NOT EXISTS OrderDish (
    order_dish_id INT PRIMARY KEY,
    dish_id INT REFERENCES Dish(dish_id) ON DELETE CASCADE,
    order_id INT REFERENCES "Order"(order_id) ON DELETE CASCADE,
    quantity FLOAT NOT NULL
);