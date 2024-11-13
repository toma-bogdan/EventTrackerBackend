package com.evenTracker.events.eventsInfo;

import com.evenTracker.events.registration.Registration;
import com.evenTracker.events.registration.RegistrationRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final LocationRepository locationRepository;
    private final RegistrationRepository registrationRepository;

    public EventService(EventRepository eventRepository, OrganizerRepository organizerRepository, LocationRepository locationRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.locationRepository = locationRepository;
        this.registrationRepository = registrationRepository;
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

    @Transactional
    public synchronized boolean scanTicket(String ticketCode){
        Registration registration = registrationRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new NoSuchElementException("Ticket code " + ticketCode + " not found"));
        if (registration.getScanned()) {
            return false;
        }
        int eventId = registration.getEventInfo().getEventId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event with id " + eventId + " not found"));

        event.setScannedTickets(event.getScannedTickets() + 1);
        registration.setScanned(true);
        registrationRepository.save(registration);
        eventRepository.save(event);
        return true;
    }
}
