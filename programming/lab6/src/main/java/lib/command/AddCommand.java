package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.*;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

// TODO: ДОБАВИТЬ ФУНКЦИОНАЛ СЧИТЫВАНИЯ ОБЪЕКТА НА КЛИЕНТЕ (ОТДЕЛЬНО)
public class AddCommand extends Command {

    public AddCommand() {
        super("add");
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner) {
        Form personForm = PersonCreationFormCreator.getForm(printWriter);
        Person newPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(scanner, newPerson);

        return newPerson;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) {
        PersonManager personManager = context.getPersonManager();

        Person newPerson = (Person) objectArgument;

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
