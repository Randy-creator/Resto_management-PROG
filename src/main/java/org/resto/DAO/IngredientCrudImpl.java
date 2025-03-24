package org.resto.DAO;

import org.resto.Entity.Ingredient;
import org.resto.Entity.Movement;
import org.resto.Entity.Unities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientCrudImpl implements IngredientCrud {
    private DbConnection db = new DbConnection();


    @Override
    public List<Ingredient> getAllIngredients(int page, int size) {
        String sql = "SELECT i.ingredient_id, i.name, i.unitPrice, i.lastModification, i.unity FROM Ingredient as i LIMIT ? OFFSET ?";

        String query = """
                SELECT m.move_id, m.moveType, m.quantity, m.stockDateModification, m.ingredient_id
                FROM Movement as m
                WHERE m.ingredient_id = ?;
                """;

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                List<Ingredient> ingredients = new ArrayList<>();

                while (rs.next()) {
                    List<Movement> moveList = new ArrayList<>();

                    try (PreparedStatement ps2 = connection.prepareStatement(query)) {
                        ps2.setInt(1, rs.getInt("ingredient_id"));
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            while (rs2.next()) {
                                moveList.add(new Movement(
                                        rs2.getInt("move_id"),
                                        rs2.getBoolean("moveType"),
                                        rs2.getDouble("quantity"),
                                        Unities.valueOf(rs.getString("unity")),
                                        rs2.getTimestamp("stockDateModification").toLocalDateTime()
                                ));
                            }
                        }
                    }

                    ingredients.add(new Ingredient(
                            rs.getInt("ingredient_id"),
                            rs.getString("name"),
                            rs.getInt("unitPrice"),
                            rs.getTimestamp("lastModification").toLocalDateTime(),
                            Unities.valueOf(rs.getString("unity")),
                            0,
                            moveList
                    ));
                }
                return ingredients;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void createUpdateIngredient(Ingredient ingredient) {
        String ingredientSql = """
                INSERT INTO Ingredient(ingredient_id, name, lastModification, unitPrice, unity)
                VALUES(?, ?, ?, ?, ?::unities)
                ON CONFLICT (ingredient_id) DO UPDATE 
                SET name = EXCLUDED.name, 
                    lastModification = EXCLUDED.lastModification, 
                    unitPrice = EXCLUDED.unitPrice, 
                    unity = EXCLUDED.unity
                """;

        String movementSql = """
                INSERT INTO Movement(move_id, moveType, quantity, unity, stockDateModification, ingredient_id)
                VALUES(?, ?, ?, ?::unities, ?, ?)
                ON CONFLICT (move_id) DO UPDATE
                SET moveType = EXCLUDED.moveType,  
                    unity = EXCLUDED.unity
                """;

        try (Connection connection = db.getConnection();
             PreparedStatement ingredientStm = connection.prepareStatement(ingredientSql);
             PreparedStatement movementStm = connection.prepareStatement(movementSql)) {

            ingredientStm.setInt(1, ingredient.getIngredient_id());
            ingredientStm.setString(2, ingredient.getName());
            ingredientStm.setTimestamp(3, Timestamp.valueOf(ingredient.getLastModification()));
            ingredientStm.setInt(4, ingredient.getUnitPrice());
            ingredientStm.setString(5, ingredient.getUnity().toString());

            ingredientStm.executeUpdate();

            for (Movement move : ingredient.getMoveHistory()) {
                movementStm.setInt(1, move.getMove_id());
                movementStm.setBoolean(2, move.isMoveType());
                movementStm.setDouble(3, move.getQuantity());
                movementStm.setString(4, move.getUnity().toString());
                movementStm.setTimestamp(5, Timestamp.valueOf(move.getStockDateModification()));
                movementStm.setInt(6, ingredient.getIngredient_id());

                movementStm.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
