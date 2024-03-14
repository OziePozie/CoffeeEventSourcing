import org.beauty.db.DatabaseConnection;
import org.beauty.entity.OrderStatus;
import org.beauty.events.OrderEvent;
import org.beauty.exceptions.OrderLifecycleIsEndedException;
import org.beauty.repositories.EventStore;
import org.beauty.repositories.sqlite.EventStoreImpl;
import org.beauty.services.OrderService;
import org.beauty.services.OrderServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderServiceTest {

    private static OrderService orderService;
    @BeforeAll
    static void setUp() {
       DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
       var eventStore = new EventStoreImpl(databaseConnection);
       orderService = new OrderServiceImpl(eventStore);
    }
    @Test
    void saveRegisterOrderEvent_shouldSaveEventToDatabase(){

        OrderEvent regEvent = registerEvent();
        orderService.publishEvent(regEvent);
    }
    @Test
    void saveCancelledEventToExistOrder_shouldSave() {

        OrderEvent cancelEvent = cancelEvent();

        OrderEvent regEvent = registerEvent();
        orderService.publishEvent(regEvent);
        orderService.publishEvent(cancelEvent);
    }

    @Test
    void saveIsInWorkEventToExistedAlreadyCancelledOrder_shouldThrowException() throws InterruptedException {

        OrderEvent regEvent = registerEvent();
        Thread.sleep(1000L);
        OrderEvent cancelEvent = cancelEvent();
        OrderEvent inWorkEvent = inWorkEvent();
        orderService.publishEvent(regEvent);
        orderService.publishEvent(cancelEvent);

        assertThrows(OrderLifecycleIsEndedException.class, () ->
                orderService.publishEvent(inWorkEvent));

    }
    @Test
    void testFindOrderByID(){
        OrderEvent regEvent = registerEvent();
        OrderEvent inWorkEvent = inWorkEvent();
        orderService.publishEvent(regEvent);
        orderService.publishEvent(inWorkEvent);
        var order = orderService.findOrder(1);
        System.out.println(order);
    }

    static void dropDB(){



        OrderEventStoreTest.dropDB();

    }

    OrderEvent registerEvent(){
        OrderEvent event = new OrderEvent();
        event.setOrderId(1);
        event.setEventType(OrderStatus.REGISTERED);
        event.setEventTime(OffsetDateTime.now());
        event.setClientId(1L);
        event.setEmployeeId(1L);
        event.setEstDeliveryTime(OffsetDateTime.now());
        event.setItemId(1L);
        event.setPrice(1.1f);

        return event;
    }

    OrderEvent cancelEvent(){
        OrderEvent event = new OrderEvent();
        event.setOrderId(1);
        event.setEventType(OrderStatus.CANCELLED);
        event.setEventTime(OffsetDateTime.now());
        event.setCancellationReason("Отмена");
        event.setEmployeeId(1L);
        return event;
    }
    OrderEvent inWorkEvent(){

        OrderEvent event = new OrderEvent();
        event.setOrderId(1);
        event.setEventType(OrderStatus.IN_WORK);
        event.setEventTime(OffsetDateTime.now());
        event.setEmployeeId(1L);
        return event;
    }


}
