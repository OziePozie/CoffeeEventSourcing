package org.beauty.services;

import org.beauty.entity.Order;
import org.beauty.events.OrderEvent;

public interface OrderService {

    void publishEvent(OrderEvent orderEvent);
    Order findOrder(int id);
}
