package server.command;

import lib.command.Command;
import lib.command.ExitCommand;
import lib.command.SaveCommand;
import server.runtime.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class ExitServerCommand extends Command {

    public ExitServerCommand() {
        super("exit");
    }

    @Override
    public boolean isClientSide() {
        return true;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) throws IOException {
        var saveCommand = new SaveCommand();
        var exitCommand = new ExitCommand();
        saveCommand.exec(printWriter, args, objectArgument, context);
        exitCommand.exec(printWriter, args, objectArgument, context);
    }

    @Override
    public String getDescription() {
        return "Exit the server.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}

