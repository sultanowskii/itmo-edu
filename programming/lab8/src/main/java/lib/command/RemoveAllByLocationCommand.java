package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Location;
import lib.schema.Person;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RemoveAllByLocationCommand extends Command {

    public RemoveAllByLocationCommand() {
        super("remove_all_by_location");
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner, ResourceBundle messageBundle) {
        Form locationForm = PersonCreationFormCreator.getLocationForm(printWriter);

        Location specifiedLocation = new Location();

        locationForm.fillObjectWithValidatedUserInput(scanner, specifiedLocation, messageBundle);

        return specifiedLocation;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        PersonManager personManager = context.getPersonManager();

        Location specifiedLocation = (Location) objectArgument;

        LinkedHashSet<Person> persons = personManager.getStorage();

        int removedElementCount = 0;

        var idsToRemove = persons
            .stream()
            .filter(
                p -> {
                    Location l = p.getLocation();
                    if (l == null) {
                        return false;
                    }
                    return l.equals(specifiedLocation);
                }
            )
            .map(Person::getID)
            .collect(Collectors.toList());

        for (int idToRemove : idsToRemove) {
            boolean deleted;
            try {
                deleted = context.getDB().deletePersonByID(user, idToRemove);
            } catch (SQLException e) {
                printWriter.println("DB error: " + e.getMessage());
                return;
            }
            if (deleted) {
                personManager.removeByID(idToRemove);
                removedElementCount++;
            }
        }

        printWriter.println("Removed " + removedElementCount + " element(s).");
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
