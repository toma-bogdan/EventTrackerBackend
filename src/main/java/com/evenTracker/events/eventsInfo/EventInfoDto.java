package com.evenTracker.events.eventsInfo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EventInfoDto {
    private Integer eventId;
    private String name;
    private String description = null;
    private BigDecimal price;
}
