package com.evenTracker.events.registration;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    Optional<Registration> findByTicketCode(String ticketCode);
    Optional<Registration> findByUserIdAndEventInfoId(Integer userId, Integer eventId);
    Optional<Registration> findByUserIdAndEventInfo_EventId(Integer userId, Integer eventId);
    List<Registration> findByUserId(Integer userId);
    List<Registration> findByEventInfoId(Integer eventInfoId);
    void deleteAllByEventInfoId(Integer eventInfoId);
}
