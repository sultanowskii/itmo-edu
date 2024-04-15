package web.lab4.server.web.auth;

import jakarta.validation.constraints.*;

public class Credentials {
    @NotNull(message="Username required")
    @NotEmpty(message="Username must not be empty")
    @Size(min=1, message="Username is too short (1 character at least)")
    private String username;

    @NotNull(message="Password required")
    @NotEmpty(message="Password must not be empty")
    @Size(min=1, message="Password is too short (1 character at least)")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}
