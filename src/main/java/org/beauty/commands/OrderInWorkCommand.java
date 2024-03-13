package org.beauty.commands;

import java.time.OffsetDateTime;

public class OrderInWorkCommand extends OrderCommand{
    public OrderInWorkCommand(Long orderId, Long employeeId, OffsetDateTime commandInitAt) {
        super(orderId, employeeId, commandInitAt);
    }
}
