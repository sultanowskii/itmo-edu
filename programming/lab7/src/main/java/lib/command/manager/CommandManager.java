package lib.command.manager;

import lib.command.Command;
import lib.command.parse.CommandInputInfo;
import server.runtime.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * Command manager. Contains commands, is responsible for calling commands
 */
public class CommandManager {
    protected final Map<String, Command> commands = new HashMap<>();

    /**
     * Default constructor
     */
    public CommandManager() {

    }

    /**
     * Constructor with a list of commands.
     * @param commands List of commands
     */
    public CommandManager(Iterable<Command> commands) {
        for (Command command : commands) {
            this.addCommand(command);
        }
    }

    /**
     * Add command to the manager
     * @param newCommand Command to be added
     */
    public void addCommand(Command newCommand) {
        this.commands.put(newCommand.getName(), newCommand);
    }

    /**
     * Get list of commands
     * @return List of commands
     */
    public ArrayList<Command> getCommands() {
        return new ArrayList<>(this.commands.values());
    }

    /**
     * Get command by its name
     * @param commandName Command name to find
     * @return Command with respective name
     * @throws NoSuchElementException If command with such name is not found
     */
    public Command getCommandByName(String commandName) throws NoSuchElementException {
        Command command = commands.get(commandName);
        if (command == null) {
            throw new NoSuchElementException("Command `" + commandName + "` not found.");
        }
        return command;
    }
}
