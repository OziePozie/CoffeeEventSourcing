package org.beauty.commands;

import lombok.Getter;

import java.time.OffsetDateTime;
@Getter
public class CancelOrderCommand extends OrderCommand{

    String cancellationReason;

    public CancelOrderCommand(Long orderId, Long employeeId, OffsetDateTime commandInitAt) {
        super(orderId, employeeId, commandInitAt);
    }
}
