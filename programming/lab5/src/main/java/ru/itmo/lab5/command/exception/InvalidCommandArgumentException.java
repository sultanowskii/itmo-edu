package ru.itmo.lab5.command.exception;

/**
 * Invalid command argument exception
 */
public class InvalidCommandArgumentException extends RuntimeException {
    public InvalidCommandArgumentException(String errorMessage) {
        super(errorMessage);
    }
}