package com.evenTracker.events.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final UserFeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(UserFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public UserFeedback saveFeedback(UserFeedback feedback) {
        return feedbackRepository.save(feedback);
    }
}