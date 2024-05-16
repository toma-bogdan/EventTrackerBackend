package com.evenTracker.events.registration;

import com.evenTracker.events.User.User;
import com.evenTracker.events.eventsInfo.EventInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "ticket_code", unique = true)
    private String ticketCode;

    @ManyToOne
    @JoinColumn(name = "event_info_id", nullable = false)
    private EventInfo eventInfo;

    @ManyToOne  // Many Registrations can belong to one User
    @JoinColumn(name = "user_id", nullable = false)  // Link to User table via user_id
    private User user;

}
