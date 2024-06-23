package com.evenTracker.events.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/profileImage")
    public ResponseEntity<UserDTO> updateUserProfile(@RequestParam String email, @RequestParam String profile_image) {
        UserDTO user = userService.updateProfileImage(email, profile_image);
        return ResponseEntity.ok(user);
    }
}