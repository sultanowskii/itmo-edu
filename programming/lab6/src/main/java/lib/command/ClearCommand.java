package lib.command;

import server.runtime.Context;
import lib.schema.Person;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear");
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) {
        LinkedHashSet<Person> persons = context.getPersonManager().getStorage();
        int removedElementCounter = persons.size();

        persons.clear();

        printWriter.println("Removed " + removedElementCounter + " element(s).");
    }


    @Override
    public String getDescription() {
        return "Clear the collection.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
