package org.beauty.commands;

import java.time.OffsetDateTime;

public class OrderIsDeliveredCommand extends OrderCommand{
    public OrderIsDeliveredCommand(Long orderId, Long employeeId, OffsetDateTime commandInitAt) {
        super(orderId, employeeId, commandInitAt);
    }
}
