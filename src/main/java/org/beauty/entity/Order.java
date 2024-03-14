package org.beauty.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
@Getter
@Setter
public class Order {

    Long orderId;
    Long clientId;
    OffsetDateTime estDeliveryTime;
    Long itemId;
    Float price;
    OffsetDateTime createdAt;
    OrderStatus currentStatus;



}
