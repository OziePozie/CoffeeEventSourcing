package org.beauty.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.beauty.entity.OrderStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@NoArgsConstructor
@Getter
@Setter
public class OrderEvent {

    Long eventId;
    Long orderId;
    Long employeeId;
    LocalDateTime handleAt;
    OrderStatus eventType;

    public OrderEvent(Long orderId, Long employeeId, LocalDateTime handleAt) {

        this.orderId = orderId;
        this.employeeId = employeeId;
        this.handleAt = handleAt;
    }

}
