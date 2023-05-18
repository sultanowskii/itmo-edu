package server.command;

import lib.command.Command;
import lib.manager.ProgramStateManager;
import server.runtime.Context;
import server.schema.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class ExitServerCommand extends Command {

    public ExitServerCommand() {
        super("exit");
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) throws IOException {
        ProgramStateManager programStateManager = ProgramStateManager.getInstance();
        printWriter.println("Exiting...");
        programStateManager.setIsRunning(false);
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

