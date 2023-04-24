package lib.command;

import server.runtime.Context;
import server.manager.PersonManager;

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
    public String getDescription() {
        return "Get general information about current collection.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
