package ru.itmo.lab5.command;

import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.runtime.Context;

import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * Execute command by its name with list of arguments
     * @param scanner Global input (to get user input)
     * @param printWriter Global output (to print something to user)
     * @param commandName Name of the command to be executed
     * @param args Command arguments
     * @param context Current context
     * @throws NoSuchElementException If command with such name is not found
     * @throws IOException If exception occurs during IO operations
     */
    public void execCommandByName(Scanner scanner, PrintWriter printWriter, String commandName, List<String> args, Context context) throws NoSuchElementException, IOException {
        Command cmd = getCommandByName(commandName);
        cmd.exec(scanner, printWriter, args, context);
    }

    /**
     * Execute command by its command input info
     * @param scanner Global input (to get user input)
     * @param printWriter Global output (to print something to user)
     * @param commandInputInfo Information about command input (name, arguments) as a singular object
     * @param context Current context
     * @throws NoSuchElementException If command with such name is not found
     * @throws IOException If exception occurs during IO operations
     */
    public void execCommandByCommandInputInfo(Scanner scanner, PrintWriter printWriter, CommandInputInfo commandInputInfo, Context context) throws NoSuchElementException, IOException {
        execCommandByName(scanner, printWriter, commandInputInfo.getCommandName(), commandInputInfo.getArgs(), context);
    }
}
