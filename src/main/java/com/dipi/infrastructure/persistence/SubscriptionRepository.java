package com.dipi.infrastructure.persistence;

import com.dipi.domain.Subscription;

public interface SubscriptionRepository {
    void save(Subscription subscription);

    Subscription findByEmail(String email);

    void update(Subscription subscription);
}
