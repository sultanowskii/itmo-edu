package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Location;
import lib.schema.Person;

import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class CountGreaterThanLocationCommand extends Command {

    public CountGreaterThanLocationCommand() {
        super("count_greater_than_location");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        PersonManager personManager = context.getPersonManager();

        Form locationForm = PersonCreationFormCreator.getLocationForm(scanner, printWriter);

        Location specifiedLocation = new Location();

        locationForm.fillObjectWithValidatedUserInput(specifiedLocation);
        LinkedHashSet<Person> persons = personManager.getStorage();

        int elementWithGreaterLocationCounter = 0;

        for (Person person : persons) {
            Location locationToCheck = person.getLocation();
            if (locationToCheck == null) {
                continue;
            }
            if (locationToCheck.compareTo(specifiedLocation) > 0) {
                elementWithGreaterLocationCounter++;
            }
        }
        printWriter.println(elementWithGreaterLocationCounter);
    }

    @Override
    public String getDescription() {
        return "Count elements which location is greater than the specified.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {location}";
    }
}
