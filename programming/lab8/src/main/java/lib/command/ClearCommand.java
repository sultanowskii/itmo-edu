package lib.command;

import server.runtime.Context;
import lib.schema.Person;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear");
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        LinkedHashSet<Person> persons = context.getPersonManager().getStorage();

        try {
            context.getDB().deleteAllPersons(user);
        } catch (SQLException e) {
            printWriter.println("DB error: " + e.getMessage());
            return false;
        }

        int removedElementCounter = persons.size();
        persons.removeIf(p -> p.getOwnerID() == user.getID());
        removedElementCounter = removedElementCounter - persons.size();

        printWriter.println("Removed " + removedElementCounter + " element(s).");
        return true;
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
