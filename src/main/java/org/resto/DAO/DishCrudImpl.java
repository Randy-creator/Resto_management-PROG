package org.resto.DAO;

import org.resto.Entity.Dish;
import org.resto.Entity.Ingredient;
import org.resto.Entity.Movement;
import org.resto.Entity.Unities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DishCrudImpl implements DishCrud {
    private DbConnection db = new DbConnection();

    @Override
    public List<Dish> getAllDishes(int page, int size) {
        String sql = "SELECT d.dish_id, d.name, d.unitPrice FROM Dish as d LIMIT ? OFFSET ?";
        String ingredientSql = """
                    SELECT i.ingredient_id, i.name, i.unitPrice, i.lastModification, di.quantity, i.unity 
                    FROM DishIngredient as di 
                    JOIN Ingredient as i ON di.ingredient_id = i.ingredient_id
                    WHERE di.dish_id = ?
                """;
        String movementSql = """
                    SELECT m.moveType, m.move_id, m.quantity, m.unity, m.stockDateModification
                    FROM Movement as m 
                    WHERE m.ingredient_id = ?
                """;

        List<Dish> dishes = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int dishId = rs.getInt("dish_id");
                    List<Ingredient> ingredientList = new ArrayList<>();

                    try (PreparedStatement ps2 = connection.prepareStatement(ingredientSql)) {
                        ps2.setInt(1, dishId);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            while (rs2.next()) {
                                int ingredientId = rs2.getInt("ingredient_id");
                                List<Movement> moveList = new ArrayList<>();

                                try (PreparedStatement ps3 = connection.prepareStatement(movementSql)) {
                                    ps3.setInt(1, ingredientId);
                                    try (ResultSet rs3 = ps3.executeQuery()) {
                                        while (rs3.next()) {
                                            moveList.add(new Movement(
                                                    rs3.getInt("move_id"),
                                                    rs3.getBoolean("moveType"),
                                                    rs3.getDouble("quantity"),
                                                    Unities.valueOf(rs3.getString("unity")),
                                                    rs3.getTimestamp("stockDateModification").toLocalDateTime()
                                            ));
                                        }
                                    }
                                }

                                ingredientList.add(new Ingredient(
                                        ingredientId,
                                        rs2.getString("name"),
                                        rs2.getInt("unitPrice"),
                                        rs2.getTimestamp("lastModification").toLocalDateTime(),
                                        Unities.valueOf(rs2.getString("unity")),
                                        rs2.getDouble("quantity"),
                                        moveList
                                ));
                            }
                        }
                    }

                    dishes.add(new Dish(
                            dishId,
                            rs.getString("name"),
                            rs.getInt("unitPrice"),
                            ingredientList
                    ));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dishes;
    }
}
