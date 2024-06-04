package com.evenTracker.events.auth;

import com.evenTracker.events.User.Role;
import com.evenTracker.events.User.User;
import com.evenTracker.events.User.UserRepository;
import com.evenTracker.events.config.JwtService;
import com.evenTracker.events.eventsInfo.Organizer;
import com.evenTracker.events.eventsInfo.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OrganizerRepository organizerRepository;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        // Verify if the email already exists
        boolean emailExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (emailExists) {
            // Handle the case where email already exists, for example, by throwing an exception
            throw new InvalidKeyException("Email already in use");
        }
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        return getAuthenticationResponse(user, request.getRole());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse createOrganizer(CreateOrganizerRequest request) throws Exception {
        // Verify if the organizer already exists
        boolean organizerExists = organizerRepository.findByName(request.getOrganizerName()).isPresent();
        if (organizerExists) {
            // Handle the case where organizer already exists, for example, by throwing an exception
            throw new InvalidKeyException("Organizer already exists");
        }
        boolean emailExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (emailExists) {
            // Handle the case where email already exists, for example, by throwing an exception
            throw new InvalidKeyException("Email already in use");
        }
        var organizer = Organizer
                .builder()
                .name(request.getOrganizerName())
                .description(request.getDescription())
                .build();
        val response = organizerRepository.save(organizer);
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .organization_id(response.getId())
                .build();
        return getAuthenticationResponse(user, request.getRole());
    }

    private AuthenticationResponse getAuthenticationResponse(User user, Role role) {
        userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
