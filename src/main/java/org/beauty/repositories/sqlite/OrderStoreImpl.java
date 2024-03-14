package org.beauty.repositories.sqlite;

import lombok.AllArgsConstructor;
import org.beauty.db.DatabaseConnection;
import org.beauty.entity.Order;
import org.beauty.repositories.OrderStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
public class OrderStoreImpl implements OrderStore {

    private DatabaseConnection connection;

    @Override
    public boolean save(Order order) {
        String query = """
                INSERT INTO orders (order_id, client_id,
                est_time_to_deliver,
                item_id,
                price,
                created_at,
                current_status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
            statement.setLong(1, order.getOrderId());
            statement.setLong(2, order.getClientId());
            statement.setObject(3, order.getEstDeliveryTime());
            statement.setLong(4, order.getItemId());
            statement.setFloat(5, order.getPrice());
            statement.setObject(6, order.getCreatedAt());
            statement.setString(7, order.getCurrentStatus().name());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findByID(Long id) {
        return null;
    }


}
