CREATE TABLE IF NOT EXISTS DishIngredient (
    dish_id INT REFERENCES Dish(dish_id) ON DELETE CASCADE,
    ingredient_id INT REFERENCES Ingredient(ingredient_id) ON DELETE CASCADE,
    quantity FLOAT NOT NULL,
    PRIMARY KEY (dish_id, ingredient_id)
);
