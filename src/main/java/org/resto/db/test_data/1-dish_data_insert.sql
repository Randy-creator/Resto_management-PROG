insert into Dish(dish_id, name, unitPrice)
values(1, 'Hot dog', 15000)
ON CONFLICT DO NOTHING;