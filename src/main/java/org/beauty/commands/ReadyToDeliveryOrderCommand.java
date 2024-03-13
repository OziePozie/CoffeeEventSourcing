package org.beauty.commands;

import java.time.OffsetDateTime;

public class ReadyToDeliveryOrderCommand extends OrderCommand{
    public ReadyToDeliveryOrderCommand(Long orderId, Long employeeId, OffsetDateTime commandInitAt) {
        super(orderId, employeeId, commandInitAt);
    }
}
