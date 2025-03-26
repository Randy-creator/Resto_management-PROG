package org.resto.DAO;

import org.postgresql.replication.fluent.physical.PhysicalReplicationOptions;
import org.resto.Entity.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderCrudImpl implements OrderCrud {
    private DbConnection db = new DbConnection();

    private StateCrudImpl statusDAO = new StateCrudImpl();
    private DishOrderCrudImpl dishOrderDAO = new DishOrderCrudImpl();

    public Order crupdateOrder(Order toCreate) {
        String sql = """
                    INSERT INTO "Order"(order_id, order_reference) VALUES(?, ?)
                    ON CONFLICT DO NOTHING
                """;


        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, toCreate.getOrderId());
            ps.setString(2, toCreate.getOrderReference());

            ps.executeUpdate();

            toCreate.getStatus().forEach(status -> {
                status.setStatus_name(Status.CREATED);
                statusDAO.insertState(status, toCreate.getOrderId());
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toCreate;
    }

    @Override
    public void save(Order toSave) {
        if (!toSave.getDishOrderList().isEmpty()) {
            toSave.getDishOrderList().forEach(dishOrder -> {
                dishOrderDAO.createDishOrder(dishOrder, toSave.getOrderId());
            });

            toSave.getStatus().forEach(status -> {
                status.setStatus_name(Status.CONFIRMED);
                statusDAO.insertState(status, toSave.getOrderId());
            });
        }

        if(!toSave.isCommandAvailable()) {
            throw new Error("Not enough dish avalaible ! :<");
        }
    }
}
