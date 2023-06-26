package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Person;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RemoveGreaterCommand extends Command {

    public RemoveGreaterCommand() {
        super("remove_greater");
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner, ResourceBundle messageBundle) {
        Form personForm = PersonCreationFormCreator.getForm(printWriter);
        Person specifiedPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(scanner, specifiedPerson, messageBundle);

        return specifiedPerson;
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        PersonManager personManager = context.getPersonManager();

        Person specifiedPerson = (Person) objectArgument;

        LinkedHashSet<Person> persons = personManager.getStorage();

        int removedElementCount = 0;

        var idsToRemove = persons
                .stream()
                .filter(person -> person.compareTo(specifiedPerson) > 0)
                .map(Person::getID).toList();

        for (int idToRemove : idsToRemove) {
            boolean deleted;
            try {
                deleted = context.getDB().deletePersonByID(user, idToRemove);
            } catch (SQLException e) {
                printWriter.println("DB error: " + e.getMessage());
                return false;
            }
            if (deleted) {
                try {
                    personManager.removeByID(idToRemove);
                    removedElementCount++;
                } catch (NoSuchElementException ignored) {}
            }
        }

        printWriter.println("Removed " + removedElementCount + " element(s).");
        return true;
    }

    @Override
    public String getDescription() {
        return "Remove all elements that are greater than the specified one.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {element}";
    }
}
