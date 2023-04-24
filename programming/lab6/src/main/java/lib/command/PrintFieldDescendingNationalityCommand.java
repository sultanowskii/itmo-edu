package lib.command;

import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Person;

import java.io.PrintWriter;
import java.util.*;

public class PrintFieldDescendingNationalityCommand extends Command {

    public PrintFieldDescendingNationalityCommand() {
        super("print_field_descending_nationality");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personManager = context.getPersonManager();

        LinkedHashSet<Person> persons = personManager.getStorage();
        List<Person> descendingPersons = new ArrayList<>(persons);
        descendingPersons.sort(Collections.reverseOrder());

        for (Person person : descendingPersons) {
            printWriter.println(person.getID());
            printWriter.println(person.getNationality());
        }
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
