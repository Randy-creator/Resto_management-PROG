insert into DishIngredient(dish_id, ingredient_id, quantity)
values(1, 1, 100),
(1, 2, 0.15),
(1, 3, 1),
(1,4,1)
ON CONFLICT DO NOTHING;