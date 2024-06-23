package com.evenTracker.events.eventComments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCommentRequestDTO {
    private String comment;
    private Integer userId;
    private Integer eventId;
}
