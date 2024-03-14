package org.beauty.repositories;

import org.beauty.events.OrderEvent;

import java.util.List;

public interface EventStore {

    boolean save(OrderEvent orderEvent);
    List<OrderEvent> findByOrderID(int ID);
    OrderEvent findLastEventByOrderID(int ID);
}
