package ru.itmo.lab5.command;

import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.manager.Context;

import java.util.*;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final Context context;

    public CommandManager( Context context) {
        this.context = context;
    }

    public void addCommand(Command newCommand) {
        this.commands.put(newCommand.getName(), newCommand);
    }

    // TODO: Подумать, как получить описание команд (и как прокидывать их)
    public ArrayList<Command> getCommands() {
        return new ArrayList<Command>(this.commands.values());
    }

    public Command getCommandByName(String commandName) throws NoSuchElementException {
        Command command = commands.get(commandName);
        if (command == null) {
            throw new NoSuchElementException("Command `" + commandName + "` not found.");
        }
        return command;
    }

    public void execCommandByName(String commandName, List<String> args) throws NoSuchElementException {
        Command cmd = getCommandByName(commandName);
        cmd.exec(args, this.context);
    }

    public void execCommandByCommandInputInfo(CommandInputInfo commandInputInfo) throws NoSuchElementException {
        execCommandByName(commandInputInfo.getCommandName(), commandInputInfo.getArgs());
    }
}
