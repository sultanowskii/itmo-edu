package ru.itmo.lab4.mental.exception;

public class NoMemoriesLeftException extends Exception {
    public NoMemoriesLeftException(String errorMessage) {
        super(errorMessage);
    }
}
