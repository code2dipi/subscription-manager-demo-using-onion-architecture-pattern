package com.dipi.exception;

public class SubscriptionServiceException extends Exception {

    public SubscriptionServiceException(String message) {
        super(message);
    }

    public SubscriptionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

