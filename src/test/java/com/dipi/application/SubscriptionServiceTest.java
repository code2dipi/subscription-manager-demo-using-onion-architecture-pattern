package com.dipi.application;

import com.dipi.domain.Subscription;
import com.dipi.infrastructure.email.EmailService;
import com.dipi.infrastructure.persistence.SubscriptionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository mockSubscriptionRepository;

    @Mock
    private EmailService mockEmailService;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenNewSubscription_whenSubscribe_thenSaveAndSendConfirmationEmail() {
        // Given
        String email = "dipi@code2dipi.com";
        Subscription subscription = new Subscription(email, false);
        doNothing().when(mockSubscriptionRepository).save(subscription);

        // When
        subscriptionService.subscribe(email);

        // Then
        verify(mockSubscriptionRepository).save(subscription);
        verify(mockEmailService).sendConfirmationEmail(email);
    }

    @Test
    public void givenUnconfirmedSubscription_whenVerifySubscription_thenUpdate() {
        // Given
        String email = "dipi@code2dipi.com";
        Subscription existingSubscription = new Subscription(email, false);
        when(mockSubscriptionRepository.findByEmail(email))
                .thenReturn(existingSubscription);

        // When
        subscriptionService.VerifySubscription(email);

        // Then
        verify(mockSubscriptionRepository).update(existingSubscription);
    }

    @Test
    public void givenNonExistentSubscription_whenVerifySubscription_thenNoUpdate() {
        // Given
        String email = "nonexistentuser@code2dipi.com";
        when(mockSubscriptionRepository.findByEmail(email)).thenReturn(null);

        // When
        subscriptionService.VerifySubscription(email);

        // Then
        verify(mockSubscriptionRepository, never()).update(any());
    }

    @Test
    public void givenAlreadyConfirmedSubscription_whenVerifySubscription_thenNoUpdate() {
        // Given
        String email = "alreadyconfirmed@code2dipi.com";
        Subscription existingSubscription = new Subscription(email, true); // Subscription already confirmed
        when(mockSubscriptionRepository.findByEmail(email)).thenReturn(existingSubscription);

        // When
        subscriptionService.VerifySubscription(email);

        // Then
        verify(mockSubscriptionRepository, never()).update(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyOrNullEmail_whenSubscribe_thenThrowException() {
        // Given
        String email = "";

        // When
        subscriptionService.subscribe(email);
    }

    @Test
    public void givenAlreadySubscribedEmail_whenSubscribe_thenNoDuplicateSubscription() {
        // Given
        String email = "alreadysubscribed@code2dipi.com";
        Subscription existingSubscription = new Subscription(email, true);
        when(mockSubscriptionRepository.findByEmail(email)).thenReturn(existingSubscription);

        // When
        subscriptionService.subscribe(email);

        // Then
        verify(mockSubscriptionRepository, never()).save(any(Subscription.class));
        verify(mockEmailService, never()).sendConfirmationEmail(anyString());
    }
}
