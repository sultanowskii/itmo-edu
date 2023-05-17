package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Location;
import lib.schema.Person;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class CountGreaterThanLocationCommand extends Command {

    public CountGreaterThanLocationCommand() {
        super("count_greater_than_location");
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner) {
        Form locationForm = PersonCreationFormCreator.getLocationForm(printWriter);

        Location specifiedLocation = new Location();

        locationForm.fillObjectWithValidatedUserInput(scanner, specifiedLocation);

        return specifiedLocation;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) {
        PersonManager personManager = context.getPersonManager();

        Location specifiedLocation = (Location) objectArgument;

        LinkedHashSet<Person> persons = personManager.getStorage();

        long elementWithGreaterLocationCounter = (
            persons
            .stream()
            .filter(
                p -> (p.getLocation().compareTo(specifiedLocation) > 0)
            )
            .count()
        );

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
