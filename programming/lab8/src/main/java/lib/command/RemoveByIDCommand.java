package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.form.field.IntegerField;
import lib.form.validation.ValidationException;
import server.runtime.Context;
import server.manager.PersonManager;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RemoveByIDCommand extends Command {

    public RemoveByIDCommand() {
        super("remove_by_id");
    }

    @Override
    public void validateArguments(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1) {
            throw new InvalidCommandArgumentException("Syntax:\n" + this.getName() + " <id>");
        }
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) throws InvalidCommandArgumentException, ValidationException {
        this.validateArguments(args);

        IntegerField idSerializer = new IntegerField("id", printWriter);
        idSerializer.setRawValue(args[0]);

        var messageBundle = context.getMessageBundle();

        try {
            idSerializer.validateRawValue(messageBundle);
            idSerializer.parseRawValue(messageBundle);
            idSerializer.validateParsedValue(messageBundle);
        } catch (ValidationException e) {
            throw new ValidationException("id: " + e.getMessage());
        }

        int idToRemove = idSerializer.getValue();

        PersonManager personManager = context.getPersonManager();
        try {
            boolean deleted;
            try {
                deleted = context.getDB().deletePersonByID(user, idToRemove);
            } catch (SQLException e) {
                printWriter.println("DB error: " + e.getMessage());
                return false;
            }
            if (deleted) {
                personManager.removeByID(idToRemove);
                printWriter.println("Removed 1 element");
            }
        } catch (NoSuchElementException e) {
            printWriter.println("Element with id=" + idToRemove + " not found.");
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Remove element by its ID.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " id";
    }
}
