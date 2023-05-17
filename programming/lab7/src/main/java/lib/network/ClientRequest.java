package lib.network;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    String commandName;
    String[] arguments;
    Serializable objectArgument;

    public ClientRequest(String commandName, String[] arguments, Serializable objectArgument) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.objectArgument = objectArgument;
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
