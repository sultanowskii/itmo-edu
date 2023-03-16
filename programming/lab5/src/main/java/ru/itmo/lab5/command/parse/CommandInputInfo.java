package ru.itmo.lab5.command.parse;

import java.util.List;

/**
 * Command info: The name and its arguments
 */
public class CommandInputInfo {
    private final String commandName;
    private final List<String> args;

    /**
     * Default constructor
     * @param name Name of the target command
     * @param args Command argument list
     */
    public CommandInputInfo(String name, List<String> args) {
        this.commandName = name;
        this.args = args;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public List<String> getArgs() {
        return this.args;
    }
}
