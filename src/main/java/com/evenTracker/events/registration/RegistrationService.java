package com.evenTracker.events.registration;

import com.evenTracker.events.User.User;
import com.evenTracker.events.eventsInfo.EventInfo;
import com.evenTracker.events.eventsInfo.EventInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evenTracker.events.User.UserRepository;

import java.security.InvalidKeyException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventInfoRepository eventInfoRepository;

    public Registration addRegistration(RegistrationDTO registrationDTO) throws InvalidKeyException {
        boolean registrationExists = registrationRepository.findByUserIdAndEventInfoId(registrationDTO.getUserId(), registrationDTO.getEventInfoId()).isPresent();
        if (registrationExists) {
            throw new InvalidKeyException("User is already registered to this event");
        }

        User user = userRepository.findById(registrationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        EventInfo eventInfo = eventInfoRepository.findById(registrationDTO.getEventInfoId())
                .orElseThrow(() -> new RuntimeException("Event Info not found"));

        // check if user is already registered to event then update ticket
        Optional<Registration> registeredToEvent = registrationRepository.findByUserIdAndEventInfo_EventId(user.getId(), eventInfo.getEventId());
        if (registeredToEvent.isPresent()) {
            return updateRegistrationEventInfo(registeredToEvent.get().getId(), registrationDTO.getEventInfoId());
        }


        Registration registration = new Registration();
        String ticket = UUID.randomUUID().toString();
        registration.setTicketCode(ticket);
        registration.setUser(user);
        registration.setEventInfo(eventInfo);
        return registrationRepository.save(registration);
    }

    public List<Registration> getAllRegistrationsByUserId(Integer userId) {
        return registrationRepository.findByUserId(userId);
    }

    public Registration updateRegistrationEventInfo(Integer registrationId, Integer eventInfoId) throws InvalidKeyException {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        EventInfo eventInfo = eventInfoRepository.findById(eventInfoId)
                .orElseThrow(() -> new RuntimeException("EventInfo not found"));
        if (eventInfo.getEventId() != registration.getEventInfo().getEventId()) {
            throw new InvalidKeyException("New ticket does not belong to current event");
        }
        registration.setEventInfo(eventInfo);
        return registrationRepository.save(registration);
    }
    public Registration findRegistrationByTicketCode(String ticketCode) {
        return registrationRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new NoSuchElementException("Ticket code " + ticketCode + " not found"));
    }

    public Optional<Registration> findRegistrationByUserIdAndEventInfoId(Integer userId, Integer eventInfoId) {
        return registrationRepository.findByUserIdAndEventInfoId(userId, eventInfoId);
    }

    public void deleteRegistration(Integer registrationId) {
        if (!registrationRepository.existsById(registrationId)) {
            throw new NoSuchElementException("Registration with ID " + registrationId + " not found");
        }
        registrationRepository.deleteById(registrationId);
    }
}
