package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.form.field.IntegerField;
import lib.form.validation.ValidationException;
import server.runtime.Context;
import server.manager.PersonManager;

import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RemoveByIDCommand extends Command {

    public RemoveByIDCommand() {
        super("remove_by_id");
    }

    @Override
    public void validateArguments(List<String> args) throws InvalidCommandArgumentException {
        if (args.size() != 1) {
            throw new InvalidCommandArgumentException("Syntax:\n" + this.getName() + " <id>");
        }
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) throws InvalidCommandArgumentException, ValidationException {
        this.validateArguments(args);

        IntegerField idSerializer = new IntegerField("id", scanner, printWriter);
        idSerializer.setRawValue(args.get(0));

        try {
            idSerializer.validateRawValue();
            idSerializer.parseRawValue();
            idSerializer.validateParsedValue();
        } catch (ValidationException e) {
            throw new ValidationException("id: " + e.getMessage());
        }

        int idToRemove = idSerializer.getValue();

        PersonManager personManager = context.getPersonManager();
        try {
            personManager.removeByID(idToRemove);
            printWriter.println("Removed 1 element");
        } catch (NoSuchElementException e) {
            printWriter.println("Element with id=" + idToRemove + " not found.");
        }
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
