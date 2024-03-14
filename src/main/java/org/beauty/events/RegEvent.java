package org.beauty.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.beauty.entity.OrderStatus;

import java.time.OffsetDateTime;
@Getter
@Setter
@ToString(callSuper = true)
public class RegEvent extends OrderEvent {

    private Long clientId;

    private OffsetDateTime estDeliveryTime;
    private Long itemId;
    private Float price;


}
