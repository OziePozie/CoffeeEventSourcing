package org.beauty.events;

import org.beauty.commands.CancelOrderCommand;

public class CancelOrderEvent extends OrderEvent{

    String cancellationReason;

    public CancelOrderEvent(CancelOrderCommand command) {
        super(command.getOrderId(),
                command.getEmployeeId(),
                command.getCommandInitAt());
        this.cancellationReason = command.getCancellationReason();

    }
}
