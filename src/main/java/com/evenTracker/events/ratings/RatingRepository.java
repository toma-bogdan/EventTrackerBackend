package com.evenTracker.events.ratings;

import com.evenTracker.events.User.User;
import com.evenTracker.events.eventsInfo.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Optional<Rating> findByEventIdAndUserId(Integer eventId, Integer userId);
    List<Rating> findAllByEventId(int eventId);
}
