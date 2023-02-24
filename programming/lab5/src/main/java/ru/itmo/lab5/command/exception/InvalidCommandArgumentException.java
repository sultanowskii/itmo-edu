package ru.itmo.lab5.command.exception;


public class InvalidCommandArgumentException extends RuntimeException {
    public InvalidCommandArgumentException(String errorMessage) {
        super(errorMessage);
    }
}