package com.evenTracker.events.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("No user found with email " + email);
        }
        return toUserDTO(user.get());
    }

    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setOrganization_id(user.getOrganization_id());
        dto.setProfile_image(user.getProfile_image());
        return dto;
    }

    public UserDTO updateProfileImage(String email, String profileImage) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("No user found with email " + email);
        }
        user.get().setProfile_image(profileImage);
        userRepository.save(user.get());
        return toUserDTO(user.get());
    }
}
