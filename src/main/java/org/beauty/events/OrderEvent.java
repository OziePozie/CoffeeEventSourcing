package org.beauty.events;

import lombok.*;
import org.beauty.entity.OrderStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderEvent {
    private int eventId;
    private int orderId;
    private OrderStatus eventType;
    private OffsetDateTime eventTime;
    private Long clientId;
    private Long employeeId;
    private OffsetDateTime estDeliveryTime;
    private Long itemId;
    private Float price;
    private String cancellationReason;


}
