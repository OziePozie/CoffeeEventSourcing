package org.beauty.commands;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@Getter
public class CancelOrderCommand extends OrderCommand{

    String cancellationReason;

    public CancelOrderCommand(Long orderId, Long employeeId, LocalDateTime commandInitAt) {
        super(orderId, employeeId, commandInitAt);
    }
}
