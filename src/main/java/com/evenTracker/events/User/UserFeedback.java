package com.evenTracker.events.User;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "feedbacks")
public class UserFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String feedbackType;
    private String satisfaction;
    private boolean newsletterSubscription;
    private String comments;
    private Integer userId;
}

