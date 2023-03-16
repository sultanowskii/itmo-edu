package ru.itmo.lab5.runtime.exception;

/**
 * Max call depth execution exception. Thrown when nested depth reaches the limit
 */
public class MaxCallDepthException extends RuntimeException {
    public MaxCallDepthException(String errorMessage) {
        super(errorMessage);
    }
}
