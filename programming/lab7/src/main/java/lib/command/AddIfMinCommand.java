package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.*;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

public class AddIfMinCommand extends Command {

    public AddIfMinCommand() {
        super("add_if_min");
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner) {
        Form personForm = PersonCreationFormCreator.getForm(printWriter);
        Person newPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(scanner, newPerson);

        return newPerson;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) {
        PersonManager personManager = context.getPersonManager();
        Person newPerson = (Person) objectArgument;

        LinkedHashSet<Person> persons = personManager.getStorage();

        Person minPerson;
        try {
            minPerson = Collections.min(persons);
        } catch (NoSuchElementException ignored) {
            minPerson = null;
        }

        int addedElementCounter = 0;

        if (minPerson == null || newPerson.compareTo(minPerson) < 0) {
            personManager.add(newPerson);
            addedElementCounter++;
        }

        printWriter.println("Added " + addedElementCounter + " element(s)");
    }

    @Override
    public String getDescription() {
        return "Add new element to the collection if the smallest.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {element}";
    }
}
