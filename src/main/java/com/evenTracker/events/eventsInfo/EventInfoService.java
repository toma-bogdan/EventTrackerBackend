package com.evenTracker.events.eventsInfo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class EventInfoService {
    private final EventInfoRepository eventInfoRepository;

    public EventInfoService(EventInfoRepository eventInfoRepository) {
        this.eventInfoRepository = eventInfoRepository;
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
            eventInfoRepository.deleteById(eventInfoId);
        } else {
            throw new Exception("Event not found with id: " + eventInfoId);
        }
    }
}
