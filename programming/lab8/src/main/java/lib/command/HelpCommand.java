package lib.command;

import lib.command.manager.CommandManager;
import server.runtime.Context;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean loginRequired() {
        return false;
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        CommandManager commandManager = context.getCommandManager();
        List<Command> commands = commandManager.getCommands();

        String longestName = commands.stream().map(Command::getName).max(Comparator.comparingInt(String::length)).orElse("");
        int commandNameSpaceSize = longestName.length();

        for (Command command : commands) {
            String paddedCommandName = String.format("%-" + commandNameSpaceSize + "s", command.getName());
            printWriter.println(paddedCommandName + " " + command.getHelp());
        }
        return true;
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
