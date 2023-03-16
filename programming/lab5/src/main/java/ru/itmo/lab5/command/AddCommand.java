package ru.itmo.lab5.command;

import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.PersonCreationFormCreator;
import ru.itmo.lab5.runtime.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import java.io.PrintWriter;
import java.util.*;

public class AddCommand extends Command {

    public AddCommand() {
        super("add");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personManager = context.getPersonManager();

        Form personForm = PersonCreationFormCreator.getForm(scanner, printWriter);

        Person newPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(newPerson);

        personManager.add(newPerson);

        printWriter.println("Element added");
    }

    @Override
    public String getDescription() {
        return "Add new element to the collection.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {element}";
    }
}
