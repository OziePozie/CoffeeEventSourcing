package org.beauty.utils;

import org.beauty.events.OrderEvent;

import java.util.Comparator;

public class OrderEventComparator implements Comparator<OrderEvent> {
    @Override
    public int compare(OrderEvent event1, OrderEvent event2) {
        return event1.getEventTime().compareTo(event2.getEventTime());
    }
}
