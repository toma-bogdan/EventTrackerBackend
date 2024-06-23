package com.evenTracker.events.eventComments;

import com.evenTracker.events.User.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCommentResponseDTO {
    private int id;
    private int eventId;
    private UserDTO user;
    private String comment;
}
