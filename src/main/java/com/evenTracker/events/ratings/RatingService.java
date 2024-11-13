package com.evenTracker.events.ratings;

import com.evenTracker.events.User.User;
import com.evenTracker.events.User.UserRepository;
import com.evenTracker.events.eventsInfo.Event;
import com.evenTracker.events.eventsInfo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public RatingResponseDTO saveRating(RatingDTO ratingDTO) {
        Optional<Rating> optionalRating = ratingRepository.findByEventIdAndUserId(ratingDTO.getEventId(), ratingDTO.getUserId());
        Rating rating;
        if (optionalRating.isPresent()) {
            rating = optionalRating.get();
            rating.setRating(ratingDTO.getRating());
        } else {
            Event event = eventRepository.findById(ratingDTO.getEventId()).orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));
            User user = userRepository.findById(ratingDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            rating = new Rating();
            rating.setRating(ratingDTO.getRating());
            rating.setUser(user);
            rating.setEvent(event);
        }
        Rating savedRating = ratingRepository.save(rating);
        return convertToDTO(savedRating);
    }

    public List<RatingResponseDTO> getRatingsByEventId(int eventId) {
        List<Rating> ratings = ratingRepository.findAllByEventId(eventId);
        return ratings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RatingResponseDTO convertToDTO(Rating rating) {
        RatingResponseDTO dto = new RatingResponseDTO();
        dto.setId(rating.getId());
        dto.setEventId(rating.getEvent().getId());
        dto.setUserId(rating.getUser().getId());
        dto.setRating(rating.getRating());
        return dto;
    }
}
