package org.beauty.commands;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
@Getter
@Setter
public class RegisterOrderCommand extends OrderCommand {

    Long clientId;

    OffsetDateTime estDeliveryTime;
    Long itemId;
    Float price;


    public RegisterOrderCommand(Long orderId, Long employeeId,
                                OffsetDateTime time, Long clientId,
                                OffsetDateTime estDeliveryTime,
                                Long itemId, Float price) {
        super(orderId, employeeId, time);

        this.clientId = clientId;
        this.estDeliveryTime = estDeliveryTime;
        this.itemId = itemId;
        this.price = price;

    }
}
