package lib.auth;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CredentialsManager {
    private Credentials credentials;
    List<Consumer<Credentials>> listeners;

    public CredentialsManager() {
        this(new Credentials());
    }

    public CredentialsManager(Credentials credentials) {
        this.credentials = credentials;
        this.listeners = new CopyOnWriteArrayList<>();
    }

    public String getLogin() {
        return credentials.getLogin();
    }

    public void setLogin(String newLogin) {
        if (!this.credentials.getLogin().equals(newLogin)) {
            this.credentials.setLogin(newLogin);
            this.notifyListeners(this.credentials);
        }
    }

    public String getPassword() {
        return credentials.getPassword();
    }

    public void setPassword(String newPassword) {
        if (!this.credentials.getPassword().equals(newPassword)) {
            this.credentials.setPassword(newPassword);
            this.notifyListeners(this.credentials);
        }
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
        this.notifyListeners(this.credentials);
    }

    public void addListener(Consumer<Credentials> listener) {
        this.listeners.add(listener);
    }

    public void notifyListeners(Credentials event) {
        for (var listener : listeners) {
            listener.accept(event);
        }
    }
}
