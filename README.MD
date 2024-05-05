# Subscription Manager(Onion Architecture)

## Overview
This demo showcases the Subscription Service using the Onion Architecture pattern. It manages subscriptions to a newsletter via email, providing functionality for subscription and verification.

## Separation of Concerns
The application follows these layers:
- **Domain Layer:** Defines the `Subscription` class for subscription data.
- **Application Layer:** Includes the `SubscriptionService` class for managing subscriptions.
- **Infrastructure Layer:** Consists of the `SubscriptionRepository` interface for data storage and retrieval, and the `EmailService` interface for sending emails.
- **Test Layer:** Contains unit tests to ensure functionality.

## Running the Application
1. Ensure Java is installed.
2. Clone the repository.
3. Build the project.
4. Run unit tests.

## Testing
Unit tests in `SubscriptionServiceTest` cover various scenarios, including subscription, verification, and error handling.

## Dependencies
- Java
- JUnit
- Mockito