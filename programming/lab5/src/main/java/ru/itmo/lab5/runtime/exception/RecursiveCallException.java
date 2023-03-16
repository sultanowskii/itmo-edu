package ru.itmo.lab5.runtime.exception;

public class RecursiveCallException extends RuntimeException {
    public RecursiveCallException(String errorMessage) {
        super(errorMessage);
    }
}

