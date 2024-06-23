package com.evenTracker.events.eventComments;

import com.evenTracker.events.User.User;
import com.evenTracker.events.User.UserRepository;
import com.evenTracker.events.User.UserService;
import com.evenTracker.events.eventsInfo.Event;
import com.evenTracker.events.eventsInfo.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventCommentsService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventCommentsRepository eventCommentsRepository;
    private final UserService userService;

    public EventCommentsService(UserRepository userRepository, EventRepository eventRepository, EventCommentsRepository eventCommentsRepository, UserService userService) {
        this.eventCommentsRepository = eventCommentsRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public EventCommentResponseDTO saveRating(EventCommentRequestDTO eventCommentRequestDTO) {
        Event event = eventRepository.findById(eventCommentRequestDTO.getEventId()).orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));
        User user = userRepository.findById(eventCommentRequestDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        EventComment eventComment = new EventComment();
        eventComment.setComment(eventCommentRequestDTO.getComment());
        eventComment.setUser(user);
        eventComment.setEvent(event);
        EventComment savedEventComm = eventCommentsRepository.save(eventComment);
        return convertToDTO(savedEventComm);
    }

    public List<EventCommentResponseDTO> getCommentsByEventId(int eventId) {
        List<EventComment> ratings = eventCommentsRepository.findAllByEventId(eventId);
        return ratings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private EventCommentResponseDTO convertToDTO(EventComment eventComment) {
        EventCommentResponseDTO dto = new EventCommentResponseDTO();
        dto.setId(eventComment.getId());
        dto.setEventId(eventComment.getEvent().getId());
        dto.setUser(userService.toUserDTO(eventComment.getUser()));
        dto.setComment(eventComment.getComment());
        return dto;
    }
}
