package org.beauty.entity;

import java.time.OffsetDateTime;

public class Order {

    Long orderId;
    Long clientId;
    Long employeeId;
    OffsetDateTime estDeliveryTime;
    Long itemId;
    Float price;
    OffsetDateTime createdAt;
    OrderStatus currentStatus;



}
