package com.evenTracker.events.eventsInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

public interface EventInfoRepository extends JpaRepository<EventInfo, Integer> {
    @Query("SELECT e FROM EventInfo e WHERE e.eventId = :eventId")
    List<EventInfo> findByEventId(@Param("eventId") int eventId);
}
