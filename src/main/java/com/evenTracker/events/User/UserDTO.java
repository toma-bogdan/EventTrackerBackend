package com.evenTracker.events.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Integer organization_id;
    private String profile_image = null;
}
