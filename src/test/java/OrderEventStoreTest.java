import org.beauty.db.DatabaseConnection;
import org.beauty.db.DatabaseInitializer;
import org.beauty.entity.OrderStatus;
import org.beauty.events.CancelEvent;
import org.beauty.events.OrderEvent;
import org.beauty.events.RegEvent;
import org.beauty.repositories.EventStore;
import org.beauty.repositories.sqlite.EventStoreImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderEventStoreTest {
    private static DatabaseConnection databaseConnection;
    private static EventStore eventStore;

    @BeforeAll
    static void setUp() {
        DatabaseInitializer.initializeDatabase();
        databaseConnection = DatabaseConnection.getInstance();
        eventStore = new EventStoreImpl(databaseConnection);
    }

    @Test
    void saveRegisterOrderEvent_shouldSaveEventToDatabase() throws SQLException {

        RegEvent testEvent = new RegEvent();
        testEvent.setOrderId(1);
        testEvent.setEventType(OrderStatus.REGISTERED);
        testEvent.setEventTime(OffsetDateTime.now());
        testEvent.setEmployeeId(1L);
        testEvent.setClientId(1L);
        testEvent.setEmployeeId(1L);
        testEvent.setEstDeliveryTime(OffsetDateTime.now());
        testEvent.setItemId(1L);
        testEvent.setPrice(1.1f);
        assertTrue(eventStore.save(testEvent));
    }

    @Test
    void saveCancelledEventToExistOrder_shouldSave() throws SQLException {

        CancelEvent testEvent = new CancelEvent();
        testEvent.setOrderId(1);
        testEvent.setEventType(OrderStatus.CANCELLED);
        testEvent.setEventTime(OffsetDateTime.now());
        testEvent.setCancellationReason("Отмена");
        testEvent.setEmployeeId(1L);
        assertTrue(eventStore.save(testEvent));
    }


    static void dropDB() {

        String query = "DELETE FROM order_events";
        databaseConnection = DatabaseConnection.getInstance();
        try {
            PreparedStatement stmt = databaseConnection.getConnection()
                    .prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
