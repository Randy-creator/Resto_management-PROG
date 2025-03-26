package org.resto.DAO;

import org.resto.Entity.State;
import org.resto.Entity.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StateCrudImpl implements StateCrud {

    private DbConnection db = new DbConnection();
    // entity:
    //    Status status_name;
    //    LocalDateTime statusModificationDate;


    // db :
    //    CREATE TABLE IF NOT EXISTS State(
    //            status_name Status PRIMARY KEY NOT NULL,
    //            order_id INT REFERENCES "Order"(order_id) ON DELETE CASCADE,
    //    order_dish_id INT REFERENCES OrderDish(order_dish_id) ON DELETE CASCADE,
    //    statusModificationDate TIMESTAMP WITH TIME ZONE
    //);

    @Override
    public State insertState(State toCreate, int orderID) {
        String sql = """
                INSERT INTO State(status_name, order_id, statusModificationDate)  
                VALUES(?::status, ?, ?)
                """;

        try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toCreate.getStatus_name().name());
            ps.setInt(2, orderID);
            ps.setTimestamp(3, Timestamp.valueOf(toCreate.getStatusModificationDate()));

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return toCreate;
    }


    @Override
    public List<State> getAllStates(int orderID) {
        String sql = """
                SELECT status_name, order_id, statusModificationDate FROM State
                WHERE order_id = ?
                """;

        List<State> stateList = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderID);


            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stateList.add(new State(
                            Status.valueOf(rs.getString("status_name")),
                            rs.getTimestamp("statusModificationDate").toLocalDateTime()
                    ));
                }
            }
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
        return stateList;
    }
}
