package ru.itmo.lab5.command;

import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.PersonCreationFormCreator;
import ru.itmo.lab5.runtime.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.Location;
import ru.itmo.lab5.schema.Person;

import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class RemoveAllByLocationCommand extends Command {

    public RemoveAllByLocationCommand() {
        super("remove_all_by_location");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personManager = context.getPersonManager();

        Form locationForm = PersonCreationFormCreator.getLocationForm(scanner, printWriter);

        Location specifiedLocation = new Location();

        locationForm.fillObjectWithValidatedUserInput(specifiedLocation);
        LinkedHashSet<Person> persons = personManager.getStorage();

        int removedCounter = 0;

        for (Person person : persons) {
            Location locationToCheck = person.getLocation();
            if (locationToCheck == null) {
                continue;
            }
            if (locationToCheck.equals(specifiedLocation)) {
                persons.remove(person);
                removedCounter++;
            }
        }
        printWriter.println("Removed " + removedCounter + " element(s).");
    }

    @Override
    public String getDescription() {
        return "Remove all elements, which location equals to the specified.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {location}";
    }
}
