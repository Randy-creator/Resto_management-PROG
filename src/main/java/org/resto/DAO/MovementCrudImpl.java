package org.resto.DAO;

import org.resto.Entity.Movement;
import org.resto.Entity.Unities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovementCrudImpl implements MovementCrud {
    private DbConnection db = new DbConnection();

    @Override
    public List<Movement> getMovementList(int page, int size) {
        String sql = """
                select m.move_id, m.moveType, m.quantity, m.stockDateModification, m.ingredient_id, m.unity
                                                from Movement as m
                                                LIMIT ? OFFSET ? 
                """;
        List<Movement> movements = new ArrayList<>();

        try (Connection connection = db.getConnection(); PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, size);
            stm.setInt(2, (page - 1) * size);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    movements.add(new Movement(rs.getInt("move_id"),
                            rs.getBoolean("moveType"),
                            rs.getDouble("quantity"),
                            Unities.valueOf(rs.getString("unity")),
                            rs.getTimestamp("stockDateModification").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movements;
    }

    @Override
    public void createMovement(Movement movement) {
        String sql = """
                INSERT INTO Movement(move_id, moveType, quantity, unity, stockDateModification)
                VALUES(?, ?, ?, ?::unities, ?)
                """;

        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, movement.getMove_id());
            ps.setBoolean(2, movement.isMoveType());
            ps.setDouble(3, movement.getQuantity());
            ps.setString(4, movement.getUnity().toString());
            ps.setTimestamp(5, Timestamp.valueOf(movement.getStockDateModification()));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating/updating movement", e);
        }
    }
}
