package ru.itmo.lab5.command;

import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.PersonCreationFormCreator;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.Person;

import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand() {
        super("remove_lower");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personManager = context.getPersonManager();

        Form personForm = PersonCreationFormCreator.getForm(scanner, printWriter);

        Person specifiedPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(specifiedPerson);
        LinkedHashSet<Person> persons = personManager.getStorage();

        int savedElementCount = persons.size();

        persons.removeIf(person -> person.compareTo(specifiedPerson) < 0);

        int removedElementCount = savedElementCount - persons.size();
        printWriter.println("Removed " + removedElementCount + " element(s).");
    }

    @Override
    public String getHelp() {
        return "Remove all elements that are less than the specified one. Syntax: " + this.getName() + " {element}";
    }
}
