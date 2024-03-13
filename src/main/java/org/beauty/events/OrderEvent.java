package org.beauty.events;

import java.time.OffsetDateTime;

public abstract class OrderEvent {

    Long orderId;
    Long employeeId;
    OffsetDateTime handleAt;

    public OrderEvent(Long orderId, Long employeeId, OffsetDateTime handleAt) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.handleAt = handleAt;
    }

}
