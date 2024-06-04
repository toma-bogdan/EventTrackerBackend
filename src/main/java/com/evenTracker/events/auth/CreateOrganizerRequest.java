package com.evenTracker.events.auth;

import com.evenTracker.events.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrganizerRequest {
    private String organizerName;
    private String description = null;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
