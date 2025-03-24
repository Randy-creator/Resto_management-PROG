CREATE TABLE IF NOT EXISTS Movement (
    move_id INT PRIMARY KEY,
    moveType BOOLEAN,
    quantity FLOAT NOT NULL,
    Unity unities NOT NULL,
    stockDateModification TIMESTAMP WITH TIME ZONE,
    ingredient_id INT REFERENCES Ingredient(ingredient_id) ON DELETE CASCADE
);