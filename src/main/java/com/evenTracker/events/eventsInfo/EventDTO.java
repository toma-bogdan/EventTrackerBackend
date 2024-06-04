package com.evenTracker.events.eventsInfo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class EventDTO {
    private String name;
    private Integer organizerId;
    private Integer locationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String imageUrl = null;
    private Category category = null;
}
