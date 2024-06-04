package com.evenTracker.events.eventsInfo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin
public class EventController {

    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository;
    private final EventInfoRepository eventInfoRepository;
    private final LocationRepository locationRepository;
    private final EventService eventService;
    private final EventInfoService eventInfoService;

    public EventController(
            OrganizerRepository organizerRepository,
            EventRepository eventRepository,
            EventInfoRepository eventInfoRepository,
            LocationRepository locationRepository,
            EventService eventService,
            EventInfoService eventInfoService
    ) {
        this.organizerRepository = organizerRepository;
        this.eventRepository = eventRepository;
        this.eventInfoRepository = eventInfoRepository;
        this.locationRepository = locationRepository;
        this.eventService = eventService;
        this.eventInfoService = eventInfoService;
    }

    @GetMapping(path = "/locations")
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @GetMapping(path = "/organizers")
    public List<Organizer> getOrganizers() {
        return organizerRepository.findAll();
    }

    @GetMapping(path = "/organizer")
    public Optional<Organizer> getOrganizerById(@RequestParam("id") int id) {
        return organizerRepository.findById(id);
    }

    @GetMapping(path = "/events")
    public List<Event> getEventsByOrganizer(@RequestParam("organizerId") int organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    @GetMapping(path = "/allEvents")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping(path = "/events/{id}")
    public Event getEventById(@PathVariable("id") int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event with id " + eventId + " not found"));
    }

    @GetMapping(path = "/multipleEvents")
    public List<Event> getEventsByIds(@RequestParam List<Integer> ids) {
        List<Event> events = eventRepository.findAllById(ids);
        if (events.isEmpty()) {
            throw new NoSuchElementException("Events with ids " + ids + " not found");
        }
        return events;
    }

    @GetMapping(path = "/eventInfo")
    public List<EventInfo> getEventInfoByEvent(@RequestParam("eventId") int eventId) {
        return eventInfoRepository.findByEventId(eventId);
    }

    @GetMapping(path = "/info")
    public Optional<EventInfo> getEventInfo(@RequestParam("id") int id) {
        return eventInfoRepository.findById(id);
    }

    @PostMapping(path = "/postEvent")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<Event> create(@RequestBody @Valid EventDTO eventDTO) {
        Event savedEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.ok(savedEvent);
    }
    @PostMapping(path = "/postEventInfo")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<EventInfo> create(@RequestBody @Valid EventInfoDto eventInfoDto) {
        EventInfo savedEventInfo = eventInfoService.createEventInfo(eventInfoDto);
        return ResponseEntity.ok(savedEventInfo);
    }

    @PostMapping(path = "/postLocation")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<Location> createLocation(@RequestBody @Valid LocationDTO locationDTO) {
        Location location = new Location();
        location.setName(locationDTO.getName());
        location.setStreet(locationDTO.getStreet());
        location.setCity(locationDTO.getCity());
        locationRepository.save(location);
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<?> deleteEvent(@PathVariable Integer eventId) {
        try {
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteEventInfo/{eventInfoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<?> deleteEventInfo(@PathVariable Integer eventInfoId) {
        try {
            eventInfoService.deleteEventInfo(eventInfoId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    
}
