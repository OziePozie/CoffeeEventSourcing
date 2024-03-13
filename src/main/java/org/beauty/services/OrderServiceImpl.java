package org.beauty.services;

import org.beauty.entity.Order;
import org.beauty.events.OrderEvent;

public class OrderServiceImpl implements OrderService{

    @Override
    public void publishEvent(OrderEvent orderEvent) {

    }

    @Override
    public Order findOrder(int id) {
        return null;
    }
}
