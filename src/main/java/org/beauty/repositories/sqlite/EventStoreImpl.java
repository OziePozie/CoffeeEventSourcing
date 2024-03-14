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
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class EventStoreImpl implements EventStore {
    private DatabaseConnection connection;

    @Override
    public boolean save(OrderEvent orderEvent) {
        return false;
    }

    @Override
    public List<OrderEvent> findByOrderID(Long ID) {
        List<OrderEvent> events = new ArrayList<>();
        String query = "SELECT event_id, employee_id,order_id, event_time, event_type FROM order_events WHERE order_id = ?";
        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)){
            stmt.setLong(1, ID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                OrderEvent event = new OrderEvent();
                event.setEventId(rs.getLong("event_id"));
                event.setEventType(OrderStatus.valueOf(rs.getString("event_type")));
                event.setOrderId(rs.getLong("order_id"));
                event.setHandleAt(rs.getTimestamp("event_time").toLocalDateTime());
                event.setEmployeeId(rs.getLong("employee_id"));
                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    return events;
    }
}
