package lib.command;

import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.Person;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class RemoveGreaterCommand extends Command {

    // TODO: ДОБАВИТЬ ФУНКЦИОНАЛ СЧИТЫВАНИЯ ОБЪЕКТА НА КЛИЕНТЕ (ОТДЕЛЬНО)
    public RemoveGreaterCommand() {
        super("remove_greater");
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner) {
        Form personForm = PersonCreationFormCreator.getForm(printWriter);
        Person specifiedPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(scanner, specifiedPerson);

        return specifiedPerson;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) {
        PersonManager personManager = context.getPersonManager();

        Person specifiedPerson = (Person) objectArgument;

        LinkedHashSet<Person> persons = personManager.getStorage();

        int savedElementCount = persons.size();

        persons.removeIf(person -> person.compareTo(specifiedPerson) > 0);

        int removedElementCount = savedElementCount - persons.size();
        printWriter.println("Removed " + removedElementCount + " element(s).");
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
