package com.evenTracker.events.ratings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResponseDTO {
    private int id;
    private int eventId;
    private int userId;
    private int rating;
}
