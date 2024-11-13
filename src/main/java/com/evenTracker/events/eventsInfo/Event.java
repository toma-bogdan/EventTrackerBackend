package com.evenTracker.events.eventsInfo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Setter
@Getter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizer_id", referencedColumnName = "id", nullable = false)
    private Organizer organizer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int scannedTickets = 0;
}
