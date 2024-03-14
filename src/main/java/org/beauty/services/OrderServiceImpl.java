package org.beauty.services;

import lombok.AllArgsConstructor;
import org.beauty.entity.Order;
import org.beauty.entity.OrderStatus;
import org.beauty.events.OrderEvent;
import org.beauty.exceptions.NotRegisteredOrderException;
import org.beauty.exceptions.OrderAlreadyRegisteredException;
import org.beauty.exceptions.OrderLifecycleIsEndedException;
import org.beauty.repositories.EventStore;
import org.beauty.utils.OrderEventComparator;

import java.util.List;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    EventStore eventStore;

    @Override
    public void publishEvent(OrderEvent orderEvent) {
        OrderStatus orderStatus = orderEvent.getEventType();
        OrderEvent lastEvent = eventStore.findLastEventByOrderID(orderEvent.getOrderId());
        if (orderStatus.equals(OrderStatus.REGISTERED) && !(lastEvent == null)){
            throw new OrderAlreadyRegisteredException();
        }

        if (!orderStatus.equals(OrderStatus.REGISTERED)){
            if (lastEvent == null){
                throw new NotRegisteredOrderException();
            } else {
                OrderStatus curStatus = lastEvent.getEventType();
                if (curStatus.equals(OrderStatus.CANCELLED) ||
                        curStatus.equals(OrderStatus.IS_DELIVERED)){
                    throw new OrderLifecycleIsEndedException();
                }
            }
        }

        eventStore.save(orderEvent);

    }

    @Override
    public Order findOrder(int id) {
        List<OrderEvent> events = eventStore.findByOrderID(id);
        events.sort(new OrderEventComparator());
        Order order = new Order();
        order.setOrderId(id);
        OrderEvent lastEvent = events.get(events.size() - 1);
        order.setCurrentStatus(lastEvent.getEventType());
        order.setEventList(events);
        return order;
    }
}
