package lib.command;

import lib.form.Form;
import lib.form.PersonRetrieveFormCreator;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.*;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class ShowCommand extends Command {

    public ShowCommand() {
        super("show");
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) {
        PersonManager personStorage = context.getPersonManager();
        HashSet<Person> persons = personStorage.getStorage();

        Form personForm = PersonRetrieveFormCreator.getForm(printWriter);

        for (Person person : persons) {
            printWriter.println(personForm.getStringifiedValueFromObject(person, 0));
        }
    }

    @Override
    public String getDescription() {
        return "Show all elements of collection.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
