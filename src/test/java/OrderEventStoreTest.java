import org.beauty.db.DatabaseConnection;
import org.beauty.entity.OrderStatus;
import org.beauty.events.OrderEvent;
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
        databaseConnection = DatabaseConnection.getInstance();
        eventStore = new EventStoreImpl(databaseConnection);
    }

    @Test
    void saveRegisterOrderEvent_shouldSaveEventToDatabase() throws SQLException {

        OrderEvent testEvent = new OrderEvent();
        testEvent.setOrderId(1);
        testEvent.setEventType(OrderStatus.REGISTERED);
        testEvent.setEventTime(OffsetDateTime.now());
        testEvent.setClientId(1L);
        testEvent.setEmployeeId(1L);
        testEvent.setEstDeliveryTime(OffsetDateTime.now());
        testEvent.setItemId(1L);
        testEvent.setPrice(1.1f);
        assertTrue(eventStore.save(testEvent));
    }
    @Test
    void saveCancelledEventToExistOrder_shouldSave() throws SQLException{

        OrderEvent testEvent = new OrderEvent();
        testEvent.setOrderId(1);
        testEvent.setEventType(OrderStatus.CANCELLED);
        testEvent.setEventTime(OffsetDateTime.now());
        testEvent.setCancellationReason("Отмена");
        testEvent.setEmployeeId(1L);
        assertTrue(eventStore.save(testEvent));
    }



    static void dropDB(){

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
