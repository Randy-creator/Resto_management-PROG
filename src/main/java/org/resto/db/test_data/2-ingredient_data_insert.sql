insert into Ingredient(ingredient_id, name, lastModification, unitPrice, unity)
values(1, 'Saucisse', '2025-01-01- 00:00', 20, 'G'),
(2, 'Huile', '2025-01-01- 00:00', 10000, 'L'),
(3, 'Oeuf', '2025-01-01- 00:00', 1000, 'U'),
(4, 'Pain', '2025-01-01- 00:00', 1000, 'U')
ON CONFLICT DO NOTHING;
