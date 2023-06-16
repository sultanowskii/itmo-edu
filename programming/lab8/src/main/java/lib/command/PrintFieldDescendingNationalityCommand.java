package lib.command;

import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Person;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

public class PrintFieldDescendingNationalityCommand extends Command {

    public PrintFieldDescendingNationalityCommand() {
        super("print_field_descending_nationality");
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        PersonManager personManager = context.getPersonManager();

        personManager.getStorage().stream().sorted(Collections.reverseOrder()).forEach(
            p -> {
                printWriter.println(p.getID());
                printWriter.println(p.getNationality());
            }
        );
    }

    @Override
    public String getDescription() {
        return "Print `nationality` field value of all elements in descending order.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
