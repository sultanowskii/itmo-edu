package ru.itmo.lab4.events.exception;

public class NotImprovableEventException extends RuntimeException {
    public NotImprovableEventException(String errorMessage) {
        super(errorMessage);
    }
}
