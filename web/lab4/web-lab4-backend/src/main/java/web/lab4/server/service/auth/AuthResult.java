package web.lab4.server.service.auth;

public class AuthResult {
    private final String errorMessage;
    private final String token;
    private final boolean successful;

    private AuthResult(String errorMessage, String token, boolean successful) {
        this.errorMessage = errorMessage;
        this.token = token;
        this.successful = successful;
    }

    public static AuthResult error(String message) {
        return new AuthResult(message, null, false);
    }

    public static AuthResult success(String token) {
        return new AuthResult(null, token, true);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getToken() {
        return token;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
