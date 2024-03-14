package org.beauty.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class CancelEvent extends OrderEvent {

    private String cancellationReason;
}
