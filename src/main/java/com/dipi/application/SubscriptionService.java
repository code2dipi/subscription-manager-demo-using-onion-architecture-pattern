package com.dipi.application;

import com.dipi.domain.Subscription;
import com.dipi.exception.SubscriptionServiceException;
import com.dipi.infrastructure.email.EmailService;
import com.dipi.infrastructure.persistence.SubscriptionRepository;

public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final EmailService emailService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, EmailService emailService) {
        this.subscriptionRepository = subscriptionRepository;
        this.emailService = emailService;
    }

    /**
     * Subscribe to a newsletter via email
     *
     * @param email
     */
    public void subscribe(String email) throws SubscriptionServiceException {
        if (email == null || email.isEmpty()) {
            throw new SubscriptionServiceException("Email cannot be null or empty");

        }
        Subscription existingSubscription = subscriptionRepository.findByEmail(email);
        if (existingSubscription != null && existingSubscription.isConfirmed()) {
            return;
        }

        Subscription subscription = new Subscription(email, false);

        try {
            subscriptionRepository.save(subscription);
            emailService.sendConfirmationEmail(email);
        } catch (Exception e) {
            throw new SubscriptionServiceException("Failed to subscribe due to database save failure", e);

        }

    }

    /**
     * Verify a subscription
     *
     * @param email
     */
    public void VerifySubscription(String email) {
        Subscription subscription = subscriptionRepository.findByEmail(email);
        if (subscription != null && !subscription.isConfirmed()) {
            subscription.setConfirmed(true);
            subscriptionRepository.update(subscription);
        }
    }

}
