package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Person;

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
    public String getDescription() {
        return "Remove all elements that are less than the specified one.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {element}";
    }
}
