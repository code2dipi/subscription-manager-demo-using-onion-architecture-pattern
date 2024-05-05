package com.dipi.domain;

import java.util.Objects;

public class Subscription {
    private String email;
    private boolean confirmed;

    public Subscription(String email, boolean confirmed) {
        this.email = email;
        this.confirmed = confirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return confirmed == that.confirmed && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, confirmed);
    }
}
