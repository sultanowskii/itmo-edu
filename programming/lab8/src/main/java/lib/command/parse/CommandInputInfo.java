package lib.command.parse;

import java.io.Serializable;
import java.util.List;

/**
 * Command info: The name and its arguments
 */
public class CommandInputInfo implements Serializable {
    private final String commandName;
    private final String[] args;

    private final Serializable additinalObject;

    public CommandInputInfo(String name, String[] args, Serializable additinalObject) {
        this.commandName = name;
        this.args = args;
        this.additinalObject = additinalObject;
    }

    public CommandInputInfo(String name, String[] args) {
        this(name, args, null);
    }

    public CommandInputInfo(String name) {
        this(name, new String[]{}, null);
    }

    public String getCommandName() {
        return this.commandName;
    }

    public String[] getArgs() {
        return this.args;
    }

    public Serializable getAdditinalObject() {
        return this.additinalObject;
    }
}
