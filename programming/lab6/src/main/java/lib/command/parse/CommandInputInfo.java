package lib.command.parse;

import java.util.List;

/**
 * Command info: The name and its arguments
 */
public class CommandInputInfo {
    private final String commandName;
    private final String[] args;

    /**
     * Default constructor
     * @param name Name of the target command
     * @param args Command argument list
     */
    public CommandInputInfo(String name, String[] args) {
        this.commandName = name;
        this.args = args;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public String[] getArgs() {
        return this.args;
    }
}
