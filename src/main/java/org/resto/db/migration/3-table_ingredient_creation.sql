create type unities as ENUM('G', 'L', 'U');

CREATE TABLE IF NOT EXISTS Ingredient (
    ingredient_id INT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    lastModification TIMESTAMP WITH TIME ZONE,
    unitPrice INT NOT NULL,
    unity unities NOT NULL
);
