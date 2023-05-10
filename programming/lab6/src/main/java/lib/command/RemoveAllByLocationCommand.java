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

public class RemoveAllByLocationCommand extends Command {

    public RemoveAllByLocationCommand() {
        super("remove_all_by_location");
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

        int removedCounter = 0;

        persons.removeIf(
            p -> {
                Location l = p.getLocation();
                if (l == null) {
                    return false;
                }
                return l.equals(specifiedLocation);
            }
        );

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
