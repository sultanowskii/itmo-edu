package lib.command;

import server.runtime.Context;
import server.manager.PersonManager;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("info");
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        PersonManager personStorage = context.getPersonManager();

        printWriter.println("Person storage.");
        printWriter.println("Init date: " + personStorage.getInitDateTime());
        printWriter.println("Number of objects in collection: " + personStorage.getStorage().size());

        return true;
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
