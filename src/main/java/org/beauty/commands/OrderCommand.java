package org.beauty.commands;

import lombok.Getter;

import java.time.OffsetDateTime;
@Getter
public abstract class OrderCommand {

    Long orderId;
    Long employeeId;
    OffsetDateTime commandInitAt;


    public OrderCommand(Long orderId, Long employeeId, OffsetDateTime commandInitAt) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.commandInitAt = commandInitAt;
    }
}
