package com.evenTracker.events.eventsInfo;

import com.evenTracker.events.registration.Registration;
import com.evenTracker.events.registration.RegistrationRepository;
import jakarta.validation.Valid;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventInfoService {
    private final EventInfoRepository eventInfoRepository;
    private final RegistrationRepository registrationRepository;

    public EventInfoService(EventInfoRepository eventInfoRepository, RegistrationRepository registrationRepository) {
        this.eventInfoRepository = eventInfoRepository;
        this.registrationRepository = registrationRepository;
    }

    public EventInfo createEventInfo(EventInfoDto eventInfoDto) {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventId(eventInfoDto.getEventId());
        eventInfo.setName(eventInfoDto.getName());
        eventInfo.setDescription(eventInfoDto.getDescription());
        eventInfo.setPrice(eventInfoDto.getPrice());
        return eventInfoRepository.save(eventInfo);
    }

    public void deleteEventInfo(Integer eventInfoId) throws Exception {
        if (eventInfoRepository.existsById(eventInfoId)) {
            List<Registration> registrations = registrationRepository.findByEventInfoId(eventInfoId);
            registrationRepository.deleteAll(registrations);
            eventInfoRepository.deleteById(eventInfoId);
        } else {
            throw new Exception("Event not found with id: " + eventInfoId);
        }
    }
}
