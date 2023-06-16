package server.runtime.exceptions;

/**
 * Max call depth execution exception. Thrown when nested depth reaches the limit
 */
public class MaxCallDepthException extends RuntimeException {
    public MaxCallDepthException(String errorMessage) {
        super(errorMessage);
    }
}
