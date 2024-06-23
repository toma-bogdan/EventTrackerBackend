package com.evenTracker.events.eventComments;

import com.evenTracker.events.ratings.RatingDTO;
import com.evenTracker.events.ratings.RatingResponseDTO;
import com.evenTracker.events.ratings.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class EventCommentsController {

    @Autowired
    private EventCommentsService eventCommentsService;

    @PostMapping
    public ResponseEntity<EventCommentResponseDTO> addComment(@RequestBody EventCommentRequestDTO eventCommentRequestDTO) {
        EventCommentResponseDTO eventCommentResponseDTO = eventCommentsService.saveRating(eventCommentRequestDTO);
        return ResponseEntity.ok(eventCommentResponseDTO);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventCommentResponseDTO>> getRatingsByEventId(@PathVariable int eventId) {
        List<EventCommentResponseDTO> eventComments = eventCommentsService.getCommentsByEventId(eventId);
        return ResponseEntity.ok(eventComments);
    }
}
