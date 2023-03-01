package ru.itmo.lab5.command;

import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.manager.Context;

import java.io.PrintWriter;
import java.util.*;

public class CommandManager {
    protected final Map<String, Command> commands = new HashMap<>();

    public CommandManager() {

    }

    public CommandManager(Iterable<Command> commands) {
        for (Command command : commands) {
            this.addCommand(command);
        }
    }

    public void addCommand(Command newCommand) {
        this.commands.put(newCommand.getName(), newCommand);
    }

    public ArrayList<Command> getCommands() {
        return new ArrayList<>(this.commands.values());
    }

    public Command getCommandByName(String commandName) throws NoSuchElementException {
        Command command = commands.get(commandName);
        if (command == null) {
            throw new NoSuchElementException("Command `" + commandName + "` not found.");
        }
        return command;
    }

    public void execCommandByName(Scanner scanner, PrintWriter printWriter, String commandName, List<String> args, Context context) throws NoSuchElementException {
        Command cmd = getCommandByName(commandName);
        cmd.exec(scanner, printWriter, args, context);
    }

    public void execCommandByCommandInputInfo(Scanner scanner, PrintWriter printWriter, CommandInputInfo commandInputInfo, Context context) throws NoSuchElementException {
        execCommandByName(scanner, printWriter, commandInputInfo.getCommandName(), commandInputInfo.getArgs(), context);
    }
}
