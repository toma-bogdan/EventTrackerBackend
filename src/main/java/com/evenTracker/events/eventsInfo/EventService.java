package com.evenTracker.events.eventsInfo;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final LocationRepository locationRepository;

    public EventService(EventRepository eventRepository, OrganizerRepository organizerRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.locationRepository = locationRepository;
    }

    public Event createEvent(EventDTO eventDTO) {
        Organizer organizer = organizerRepository.findById(eventDTO.getOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid organizer ID: " + eventDTO.getOrganizerId()));
        Location location = locationRepository.findById(eventDTO.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID: " + eventDTO.getLocationId()));
        Event event = new Event();
        return getEvent(eventDTO, event, organizer, location);
    }
    public Event editEvent(Integer eventId, EventDTO eventDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event ID: " + eventId));
        Organizer organizer = organizerRepository.findById(eventDTO.getOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid organizer ID: " + eventDTO.getOrganizerId()));
        Location location = locationRepository.findById(eventDTO.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID: " + eventDTO.getLocationId()));

        return getEvent(eventDTO, event, organizer, location);
    }

    @NotNull
    private Event getEvent(EventDTO eventDTO, Event event, Organizer organizer, Location location) {
        event.setName(eventDTO.getName());
        event.setOrganizer(organizer);
        event.setLocation(location);
        event.setStartDate(LocalDate.from(eventDTO.getStartDate()));
        event.setEndDate(LocalDate.from(eventDTO.getEndDate()));
        event.setDescription(eventDTO.getDescription());
        event.setImageUrl(eventDTO.getImageUrl());
        event.setCategory(eventDTO.getCategory());
        return eventRepository.save(event);
    }

    public void deleteEvent(Integer eventId) throws Exception {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
        } else {
            throw new Exception("Event not found with id: " + eventId);
        }
    }
}
