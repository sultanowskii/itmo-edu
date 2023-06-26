package client.command;

import lib.command.Command;
import server.runtime.Context;
import lib.manager.ProgramStateManager;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ExitClientCommand extends Command {

    public ExitClientCommand() {
        super("exit");
    }

    @Override
    public boolean isClientSide() {
        return true;
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner, ResourceBundle messageBundle) {
        return super.getAdditionalObjectFromUser(printWriter, scanner, messageBundle);
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        ProgramStateManager programStateManager = ProgramStateManager.getInstance();
        printWriter.println("Exiting...");
        programStateManager.setIsRunning(false);
        return true;
    }

    @Override
    public String getDescription() {
        return "Exit the CLI.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
