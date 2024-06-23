package com.evenTracker.events.eventComments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventCommentsRepository extends JpaRepository<EventComment, Integer> {
    List<EventComment> findAllByEventId(int eventId);
}
