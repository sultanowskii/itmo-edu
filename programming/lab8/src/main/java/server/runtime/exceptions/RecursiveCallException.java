package server.runtime.exceptions;

public class RecursiveCallException extends RuntimeException {
    public RecursiveCallException(String errorMessage) {
        super(errorMessage);
    }
}

