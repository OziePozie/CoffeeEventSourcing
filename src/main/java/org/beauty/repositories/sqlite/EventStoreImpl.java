package org.beauty.repositories.sqlite;

import lombok.AllArgsConstructor;
import org.beauty.db.DatabaseConnection;
import org.beauty.entity.OrderStatus;
import org.beauty.events.CancelEvent;
import org.beauty.events.OrderEvent;
import org.beauty.events.RegEvent;
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
                    RegEvent regEvent = (RegEvent) event;
                    try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
                        statement.setLong(1, regEvent.getOrderId());
                        statement.setString(2, regEvent.getEventType().toString());
                        statement.setTimestamp(3, Timestamp.from(regEvent.getEventTime().toInstant()));
                        statement.setLong(4, regEvent.getClientId());
                        statement.setLong(5, regEvent.getEmployeeId());
                        statement.setTimestamp(6, Timestamp.from(regEvent.getEstDeliveryTime().toInstant()));
                        statement.setLong(7, regEvent.getItemId());
                        statement.setFloat(8, regEvent.getPrice());
                        return statement.executeUpdate() > 0;

                    }

                case CANCELLED:
                    query = "INSERT INTO order_events (order_id, event_type, event_time, employee_id, cancellation_reason) " +
                            "VALUES (?, ?, ?, ?, ?)";
                    CancelEvent cancelEvent = (CancelEvent) event;

                    try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
                        statement.setLong(1, cancelEvent.getOrderId());
                        statement.setString(2, cancelEvent.getEventType().toString());
                        statement.setTimestamp(3, Timestamp.from(cancelEvent.getEventTime().toInstant()));
                        statement.setLong(4, cancelEvent.getEmployeeId());
                        statement.setString(5, cancelEvent.getCancellationReason());

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
        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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
        OrderStatus eventType = OrderStatus.valueOf(resultSet.getString("event_type"));
        OrderEvent event;

        switch (eventType) {
            case REGISTERED:
                RegEvent regEvent = new RegEvent();
                regEvent.setClientId(resultSet.getLong("client_id"));
                regEvent.setPrice(resultSet.getFloat("price"));
                regEvent.setEstDeliveryTime(resultSet.getTimestamp("est_delivery_time")
                        .toLocalDateTime()
                        .atOffset(ZoneOffset.UTC));
                regEvent.setItemId(resultSet.getLong("item_id"));
                event = regEvent;
                break;
            case CANCELLED:
                CancelEvent cancelEvent = new CancelEvent();
                cancelEvent.setCancellationReason(resultSet.getString("cancellation_reason"));
                event = cancelEvent;
                break;
            default:
                event = new OrderEvent();
                break;
        }

        event.setEventId(resultSet.getInt("event_id"));
        event.setOrderId(resultSet.getInt("order_id"));
        event.setEventType(eventType);
        event.setEmployeeId(resultSet.getLong("employee_id"));
        event.setEventTime(resultSet.getTimestamp("event_time")
                .toInstant()
                .atOffset(ZoneOffset.UTC));

        return event;
    }


}
