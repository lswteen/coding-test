package com.illuminarean.example.error;

public class FailedSendNotificationException extends RuntimeException {

    public FailedSendNotificationException(String message) {
        super(message);
    }

    public FailedSendNotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
