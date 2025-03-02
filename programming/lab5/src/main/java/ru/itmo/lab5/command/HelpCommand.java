package ru.itmo.lab5.command;

import ru.itmo.lab5.runtime.Context;

import java.io.PrintWriter;
import java.util.*;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        CommandManager commandManager = context.getCommandManager();
        List<Command> commands = commandManager.getCommands();

        String longestName = commands.stream().map(Command::getName).max(Comparator.comparingInt(String::length)).orElse("");
        int commandNameSpaceSize = longestName.length();

        for (Command command : commands) {
            String paddedCommandName = String.format("%-" + commandNameSpaceSize + "s", command.getName());
            printWriter.println(paddedCommandName + " " + command.getHelp());
        }
    }

    @Override
    public String getDescription() {
        return "Get help message (this one).";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
