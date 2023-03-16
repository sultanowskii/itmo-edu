package ru.itmo.lab5.command;

import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.PersonCreationFormCreator;
import ru.itmo.lab5.runtime.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import java.io.PrintWriter;
import java.util.*;

public class AddIfMinCommand extends Command {

    public AddIfMinCommand() {
        super("add_if_min");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personManager = context.getPersonManager();

        Form personForm = PersonCreationFormCreator.getForm(scanner, printWriter);

        Person newPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(newPerson);
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
