package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import lib.form.field.*;
import lib.form.validation.*;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.*;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UpdateCommand extends Command {

    public UpdateCommand() {
        super("update");
    }

    @Override
    public void validateArguments(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1) {
            throw new InvalidCommandArgumentException("Syntax:\n" + this.getName() + " <id>");
        }
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner, ResourceBundle messageBundle) {
        Form personForm = PersonCreationFormCreator.getForm(printWriter);
        Person specifiedPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(scanner, specifiedPerson, messageBundle);

        return specifiedPerson;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        this.validateArguments(args);

        var messageBundle = context.getMessageBundle();

        IntegerField idSerializer = new IntegerField("id", printWriter);
        idSerializer.setRawValue(args[0]);

        try {
            idSerializer.validateRawValue(messageBundle);
            idSerializer.parseRawValue(messageBundle);
            idSerializer.validateParsedValue(messageBundle);
        } catch (ValidationException e) {
            throw new ValidationException("id: " + e.getMessage());
        }

        Person updatedPerson = (Person) objectArgument;
        int idToUpdate = idSerializer.getValue();

        boolean updated;
        try {
            updated = context.getDB().updatePerson(user, idToUpdate, updatedPerson);
        } catch (SQLException e) {
            printWriter.println("DB error: " + e.getMessage());
            return;
        }

        if (!updated) {
            printWriter.println("Didn't update person for unknown reason. Try again later.");
        }

        PersonManager personManager = context.getPersonManager();

        // remove old one with the specified id
        try {
            personManager.removeByID(idToUpdate);
        } catch (NoSuchElementException e) {
            printWriter.println("Element with id=" + idToUpdate + " not found.");
        }

        // and add a new one with the same id
        updatedPerson.setID(idToUpdate);
        personManager.add(updatedPerson);

        printWriter.println("Element updated.");
    }

    @Override
    public String getDescription() {
        return "Update an element with id=`id`.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " id {element}";
    }
}
