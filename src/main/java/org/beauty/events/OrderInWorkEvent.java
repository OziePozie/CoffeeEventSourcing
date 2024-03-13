package org.beauty.events;

import org.beauty.commands.OrderInWorkCommand;

import java.time.OffsetDateTime;

public class OrderInWorkEvent extends OrderEvent{
    public OrderInWorkEvent(OrderInWorkCommand command) {
        super(command.getOrderId(),
                command.getEmployeeId(),
                command.getCommandInitAt());
    }
}
