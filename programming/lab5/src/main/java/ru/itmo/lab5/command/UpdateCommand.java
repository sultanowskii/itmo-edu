package ru.itmo.lab5.command;

import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.PersonCreationFormCreator;
import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.form.validation.*;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateCommand extends Command {

    public UpdateCommand() {
        super("update");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        // TODO: Вынести обработку аргументов
        if (args.size() != 1) {
            throw new InvalidCommandArgumentException("Syntax:\n" + this.getName() + " <id>");
        }

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
    public String getHelp() {
        return "Update an element with id=`id`. Syntax: " + this.getName() + " id {element}";
    }
}
