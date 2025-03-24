insert into Dish(dish_id, name, unitPrice)
values(1, 'Hot dog', 15000)
ON CONFLICT DO NOTHING;

insert into Ingredient(ingredient_id, name, lastModification, unitPrice, unity)
values(1, 'Saucisse', '2025-01-01- 00:00', 20, 'G'),
(2, 'Huile', '2025-01-01- 00:00', 10000, 'L'),
(3, 'Oeuf', '2025-01-01- 00:00', 1000, 'U'),
(4, 'Pain', '2025-01-01- 00:00', 1000, 'U')
ON CONFLICT DO NOTHING;

insert into DishIngredient(dish_id, ingredient_id, quantity)
values(1, 1, 100),
(1, 2, 0.15),
(1, 3, 1),
(1,4,1)
ON CONFLICT DO NOTHING;


insert into Movement(move_id, moveType, quantity, unity, stockDateModification, ingredient_id)
values
    (1,true, 100, 'U', '2025-02-01 08:00', 3), -- oeuf
    (2,true, 50, 'U', '2025-02-01 08:00', 2), -- pain
    (3,true, 10000, 'G', '2025-02-01 08:00', 1), -- saucisse
    (4,true, 20, 'L', '2025-02-01 08:00', 4) -- huile
    ON CONFLICT DO NOTHING;


'2025-03-20 10:00'