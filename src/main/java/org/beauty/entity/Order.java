package org.beauty.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.beauty.events.OrderEvent;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {

    int orderId;
    OrderStatus currentStatus;
    List<OrderEvent> eventList;



}
