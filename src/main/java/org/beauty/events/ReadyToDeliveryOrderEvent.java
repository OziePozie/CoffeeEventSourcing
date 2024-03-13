package org.beauty.events;

import org.beauty.commands.ReadyToDeliveryOrderCommand;

import java.time.OffsetDateTime;

public class ReadyToDeliveryOrderEvent extends OrderEvent{
    public ReadyToDeliveryOrderEvent(ReadyToDeliveryOrderCommand command) {
        super(command.getOrderId(),
                command.getEmployeeId(),
                command.getCommandInitAt());
    }
}
