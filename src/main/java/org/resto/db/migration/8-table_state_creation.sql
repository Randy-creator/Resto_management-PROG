CREATE TABLE IF NOT EXISTS State(
    status_name Status PRIMARY KEY NOT NULL,
    order_id INT REFERENCES "Order"(order_id) ON DELETE CASCADE,
    order_dish_id INT REFERENCES OrderDish(order_dish_id) ON DELETE CASCADE,
    statusModificationDate TIMESTAMP WITH TIME ZONE
);
