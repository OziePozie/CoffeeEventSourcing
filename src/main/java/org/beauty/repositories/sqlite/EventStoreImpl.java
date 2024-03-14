package org.beauty.repositories.sqlite;

import lombok.AllArgsConstructor;
import org.beauty.db.DatabaseConnection;
import org.beauty.entity.OrderStatus;
import org.beauty.events.OrderEvent;
import org.beauty.repositories.EventStore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class EventStoreImpl implements EventStore {
    private DatabaseConnection connection;

    @Override
    public boolean save(OrderEvent event) {
        String query;
        try {
            switch (event.getEventType()) {
                case REGISTERED:
                    query = "INSERT INTO order_events (order_id, event_type, event_time, client_id, employee_id, est_delivery_time, item_id, price) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
                        statement.setLong(1, event.getOrderId());
                        statement.setString(2, event.getEventType().toString());
                        statement.setTimestamp(3, Timestamp.from(event.getEventTime().toInstant()));
                        statement.setLong(4, event.getClientId());
                        statement.setLong(5, event.getEmployeeId());
                        statement.setTimestamp(6, Timestamp.from(event.getEstDeliveryTime().toInstant()));
                        statement.setLong(7, event.getItemId());
                        statement.setFloat(8, event.getPrice());
                        return statement.executeUpdate() > 0;

                    }

                case CANCELLED:
                    query = "INSERT INTO order_events (order_id, event_type, event_time, employee_id, cancellation_reason) " +
                            "VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
                        statement.setLong(1, event.getOrderId());
                        statement.setString(2, event.getEventType().toString());
                        statement.setTimestamp(3, Timestamp.from(event.getEventTime().toInstant()));
                        statement.setLong(4, event.getEmployeeId());
                        statement.setString(5, event.getCancellationReason());

                        return statement.executeUpdate() > 0;
                    }

                case IN_WORK:
                case IS_READY:
                case IS_DELIVERED:
                    query = "INSERT INTO order_events (order_id, event_type, event_time, employee_id) " +
                            "VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
                        statement.setLong(1, event.getOrderId());
                        statement.setString(2, event.getEventType().toString());
                        statement.setTimestamp(3, Timestamp.from(event.getEventTime().toInstant()));
                        statement.setLong(4, event.getEmployeeId());
                        return statement.executeUpdate() > 0;
                    }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<OrderEvent> findByOrderID(int ID) {
        List<OrderEvent> events = new ArrayList<>();
        String query = """
                        
                SELECT * FROM order_events WHERE order_id = ?
                        """;
        try (PreparedStatement stmt = connection.getConnection() .prepareStatement(query)){
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                var event = mapResultSetToOrderEvent(rs);
                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    return events;
    }

    @Override
    public OrderEvent findLastEventByOrderID(int ID) {
        String query = "SELECT * FROM order_events WHERE order_id = ? " +
                "ORDER BY event_time DESC LIMIT 1";
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
            statement.setLong(1, ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToOrderEvent(resultSet);
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }



    private OrderEvent mapResultSetToOrderEvent(ResultSet resultSet) throws SQLException {
        OrderEvent event = new OrderEvent();
        try {


        event.setEventId(resultSet.getInt("event_id"));
        event.setEventType(OrderStatus.valueOf(resultSet.getString("event_type")));
        event.setOrderId(resultSet.getInt("order_id"));
        event.setEmployeeId(resultSet.getLong("employee_id"));
        event.setEventTime(resultSet.getTimestamp("event_time")
                .toInstant()
                .atOffset(ZoneOffset.UTC));
        event.setClientId(resultSet.getLong("client_id"));
        event.setPrice(resultSet.getFloat("price"));
        event.setEstDeliveryTime(resultSet.getTimestamp("est_delivery_time")
                .toLocalDateTime()
                .atOffset(ZoneOffset.UTC));
        event.setItemId(resultSet.getLong("item_id"));
        event.setCancellationReason(resultSet.getString("cancellation_reason"));
        } catch (Exception ignored){

        }
        return event;
    }

}
