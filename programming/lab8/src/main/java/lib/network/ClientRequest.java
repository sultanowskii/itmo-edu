package lib.network;

import lib.auth.Credentials;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    Credentials credentials;
    String commandName;
    String[] arguments;
    Serializable objectArgument;

    public ClientRequest(Credentials credentials, String commandName, String[] arguments, Serializable objectArgument) {
        this.credentials = credentials;
        this.commandName = commandName;
        this.arguments = arguments;
        this.objectArgument = objectArgument;
    }

    public ClientRequest(String login, String password, String commandName, String[] arguments, Serializable objectArgument) {
        this(new Credentials(login, password), commandName, arguments, objectArgument);
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public Serializable getObjectArgument() {
        return objectArgument;
    }

    public void setObjectArgument(Serializable objectArgument) {
        this.objectArgument = objectArgument;
    }
}
