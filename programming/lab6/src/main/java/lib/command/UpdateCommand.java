package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.form.Form;
import lib.form.PersonCreationFormCreator;
import lib.form.field.*;
import lib.form.validation.*;
import server.runtime.Context;
import server.manager.PersonManager;
import lib.schema.*;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class UpdateCommand extends Command {

    public UpdateCommand() {
        super("update");
    }

    @Override
    public void validateArguments(List<String> args) throws InvalidCommandArgumentException {
        if (args.size() != 1) {
            throw new InvalidCommandArgumentException("Syntax:\n" + this.getName() + " <id>");
        }
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
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

        int idToUpdate = idSerializer.getValue();

        PersonManager personManager = context.getPersonManager();
        Person personToUpdate = personManager.getByID(idToUpdate);

        Form personForm = PersonCreationFormCreator.getForm(scanner, printWriter);

        personForm.fillObjectWithValidatedUserInput(personToUpdate);

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
