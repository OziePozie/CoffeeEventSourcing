package org.beauty.events;

import org.beauty.commands.OrderIsDeliveredCommand;

import java.time.OffsetDateTime;

public class OrderIsDeliveredEvent extends OrderEvent{


    public OrderIsDeliveredEvent(OrderIsDeliveredCommand command) {
        super(command.getOrderId(),
                command.getEmployeeId(),
                command.getCommandInitAt());
    }
}
