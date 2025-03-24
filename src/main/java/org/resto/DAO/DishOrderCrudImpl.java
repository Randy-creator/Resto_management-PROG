package org.resto.DAO;

import org.resto.Entity.DishOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DishOrderCrudImpl implements DishOrderCrud {
    private DbConnection db = new DbConnection();

    @Override
    public DishOrder createDishOrder(DishOrder toCreate, int orderId) {
        String sql = """
            INSERT INTO OrderDish (order_dish_id, dish_id, order_id, quantity) 
            VALUES (?, ?, ?, ?) 
            ON CONFLICT (order_dish_id) DO UPDATE SET quantity = excluded.quantity
        """;

        try (Connection connection = db.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, toCreate.getDishOrderId());
            stmt.setInt(2, toCreate.getDishOrdered().getDish_id());
            stmt.setInt(3, orderId);
            stmt.setDouble(4, toCreate.getQuantityOrdered());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Commande ajoutée ou mise à jour avec succès !");
            } else {
                System.out.println("Aucune ligne affectée. Vérifiez les valeurs.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout ou de la mise à jour de la commande.", e);
        }

        return toCreate;
    }
}
