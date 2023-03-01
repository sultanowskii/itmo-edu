package ru.itmo.lab5.command;

import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("info");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personStorage = context.getPersonManager();
        printWriter.println("Person storage.");
        printWriter.println("Init date: " + personStorage.getInitDateTime());
        printWriter.println("Number of objects in collection: " + personStorage.getStorage().size());
    }

    @Override
    public String getHelp() {
        return "Get general information about current collection. Syntax: " + this.getName();
    }
}
