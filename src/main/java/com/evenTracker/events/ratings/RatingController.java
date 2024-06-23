package com.evenTracker.events.ratings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponseDTO> createOrUpdateRating(@RequestBody RatingDTO ratingDTO) {
        RatingResponseDTO savedRatingDTO = ratingService.saveRating(ratingDTO);
        return ResponseEntity.ok(savedRatingDTO);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsByEventId(@PathVariable int eventId) {
        List<RatingResponseDTO> ratings = ratingService.getRatingsByEventId(eventId);
        return ResponseEntity.ok(ratings);
    }
}