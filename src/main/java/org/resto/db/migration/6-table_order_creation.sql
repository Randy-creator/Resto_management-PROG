create type Status as ENUM('CREATED', 'CONFIRMED', 'IN_PROGRESS', 'DONE', 'SERVED');

CREATE TABLE IF NOT EXISTS "Order" (
    order_id INT PRIMARY KEY,
    order_date TIMESTAMP,
);