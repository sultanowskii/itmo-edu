package ru.itmo.lab5.command;

import ru.itmo.lab5.runtime.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.Person;

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
