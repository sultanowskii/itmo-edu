package ru.itmo.lab5.command;

import ru.itmo.lab5.runtime.Context;
import ru.itmo.lab5.manager.ProgramStateManager;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        ProgramStateManager programStateManager = ProgramStateManager.getInstance();
        printWriter.println("Exiting...");
        programStateManager.setIsRunning(false);
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
