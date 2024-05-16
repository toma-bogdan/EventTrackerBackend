package com.evenTracker.events.registration;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registration")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@CrossOrigin
public class RegistrationController {

    private final RegistrationService registrationService;
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody RegistrationDTO registrationDTO) throws InvalidKeyException {
        Registration savedRegistration = registrationService.addRegistration(registrationDTO);
        return ResponseEntity.ok(savedRegistration);
    }

    @GetMapping("/{ticketCode}")
    public ResponseEntity<Registration> get(@PathVariable String ticketCode) {
        Registration registration = registrationService.findRegistrationByTicketCode(ticketCode);
        return ResponseEntity.ok(registration);
    }

    @GetMapping("/check")
    public Optional<ResponseEntity<Registration>> getRegistration(
            @RequestParam("userId") Integer userId,
            @RequestParam("eventInfoId") Integer eventInfoId) {
        Optional<Registration> registration = registrationService.findRegistrationByUserIdAndEventInfoId(userId, eventInfoId);
        return registration
                .map(ResponseEntity::ok);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Registration>> getRegistrationsByUser(@PathVariable Integer userId) {
        List<Registration> registrations = registrationService.getAllRegistrationsByUserId(userId);
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @PutMapping("/{registrationId}/{eventInfoId}")
    public ResponseEntity<Registration> updateRegistrationEventInfo(
            @PathVariable Integer registrationId,
            @PathVariable Integer eventInfoId) throws InvalidKeyException {

        Registration updatedRegistration = registrationService.updateRegistrationEventInfo(registrationId, eventInfoId);
        return ResponseEntity.ok(updatedRegistration);
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<?> deleteRegistration(@PathVariable Integer registrationId) {
        registrationService.deleteRegistration(registrationId);
        return ResponseEntity.ok().build();
    }
}
