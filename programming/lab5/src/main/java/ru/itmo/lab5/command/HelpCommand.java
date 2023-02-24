package ru.itmo.lab5.command;

import ru.itmo.lab5.manager.Context;

import java.io.PrintWriter;
import java.util.*;

public class HelpCommand extends Command {

    public HelpCommand(Scanner scanner, PrintWriter printWriter) {
        super("help", scanner, printWriter);
    }

    @Override
    public void exec(List<String> args, Context context) {
        CommandManager commandManager = context.getCommandManager();
        List<Command> commands = commandManager.getCommands();

        String longestName = commands.stream().map(Command::getName).max(Comparator.comparingInt(String::length)).orElse("");
        int commandNameSpaceSize = longestName.length();

        for (Command command : commands) {
            // TODO: вынести
            String paddedCommandName = String.format("%-" + commandNameSpaceSize + "s", command.getName());
            printWriter.println(paddedCommandName + " " + command.getHelp());
        }
    }

    @Override
    public String getHelp() {
        return "Get help message (this one). Syntax: " + this.getName();
    }
}
