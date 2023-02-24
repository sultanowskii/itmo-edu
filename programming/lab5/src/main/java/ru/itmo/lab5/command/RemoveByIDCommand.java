package ru.itmo.lab5.command;

import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.form.field.IntegerField;
import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.Person;

import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RemoveByIDCommand extends Command {

    public RemoveByIDCommand(Scanner scanner, PrintWriter printWriter) {
        super("remove_by_id", scanner, printWriter);
    }

    @Override
    public void exec(List<String> args, Context context) throws InvalidCommandArgumentException, ValidationException {
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

        int idToRemove = idSerializer.getValue();

        PersonManager personManager = context.getPersonManager();
        try {
            personManager.removeByID(idToRemove);
        } catch (NoSuchElementException e) {
            printWriter.println("Element with id=" + idToRemove + " not found.");
        }
    }

    @Override
    public String getHelp() {
        return "Remove element by its ID. Syntax: " + this.getName() + " id";
    }
}
