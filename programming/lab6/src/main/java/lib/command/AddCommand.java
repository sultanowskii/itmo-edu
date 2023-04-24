package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.*;

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
