package org.beauty.events;

import org.beauty.commands.RegisterOrderCommand;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class RegisterOrderEvent extends OrderEvent{



    Long clientId;
    LocalDateTime estDeliveryTime;
    Long itemId;
    Float price;


    public RegisterOrderEvent(RegisterOrderCommand command) {

        super(command.getOrderId(),
                command.getEmployeeId(),
                command.getCommandInitAt());
        this.clientId = command.getClientId();
        this.estDeliveryTime = command.getEstDeliveryTime();
        this.itemId = command.getItemId();
        this.price = command.getPrice();

    }

}
