package ru.itmo.lab5.command;

import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.ProgramStateManager;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ExitCommand extends Command {

    public ExitCommand(Scanner scanner, PrintWriter printWriter) {
        super("exit", scanner, printWriter);
    }

    @Override
    public void exec(List<String> args, Context context) {
        ProgramStateManager programStateManager = ProgramStateManager.getInstance();
        printWriter.println("Exiting...");
        programStateManager.setIsRunning(false);
    }

    @Override
    public String getHelp() {
        return "Exit the CLI. Syntax: " + this.getName();
    }
}
