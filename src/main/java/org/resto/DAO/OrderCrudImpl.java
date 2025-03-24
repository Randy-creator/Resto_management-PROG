package org.resto.DAO;

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

    public List<Order> crupdateOrder(Order toCreate) {
        String sql = """
                    INSERT INTO "Order"(order_id, order_reference) VALUES(?, ?)
                    ON CONFLICT DO UPDATE SET order_id = excluded.order_id, order_reference = excluded.order_reference
                """;

        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, toCreate.getOrderId());
            ps.setString(2, toCreate.getOrderReference());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
