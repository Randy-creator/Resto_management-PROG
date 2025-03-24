insert into Movement(move_id, moveType, quantity, unity, stockDateModification, ingredient_id)
values
    (1,true, 100, 'U', '2025-02-01 08:00', 3), -- oeuf
    (2,true, 50, 'U', '2025-02-01 08:00', 2), -- pain
    (3,true, 10000, 'G', '2025-02-01 08:00', 1), -- saucisse
    (4,true, 20, 'L', '2025-02-01 08:00', 4) -- huile
    ON CONFLICT DO NOTHING;