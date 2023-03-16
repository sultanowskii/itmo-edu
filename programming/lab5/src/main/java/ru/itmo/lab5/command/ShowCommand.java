package ru.itmo.lab5.command;

import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.PersonRetrieveFormCreator;
import ru.itmo.lab5.runtime.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class ShowCommand extends Command {

    public ShowCommand() {
        super("show");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personStorage = context.getPersonManager();
        HashSet<Person> persons = personStorage.getStorage();

        Form personForm = PersonRetrieveFormCreator.getForm(scanner, printWriter);

        for (Person person : persons) {
            printWriter.println(personForm.getStringifiedValueFromObject(person, 0));
        }
    }

    @Override
    public String getDescription() {
        return "Show all elements of collection.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
