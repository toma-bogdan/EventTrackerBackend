package com.evenTracker.events.ratings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
    private Integer rating;
    private Integer userId;
    private Integer eventId;
}

