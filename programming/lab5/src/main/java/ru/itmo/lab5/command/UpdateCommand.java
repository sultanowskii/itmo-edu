package ru.itmo.lab5.command;

import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.form.validation.NonNullValidator;
import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateCommand extends Command {

    public UpdateCommand(Scanner scanner, PrintWriter printWriter) {
        super("update", scanner, printWriter);
    }

    @Override
    public void exec(List<String> args, Context context) {
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

        List<Field<?>> coordinatesFields = new ArrayList<>();
        coordinatesFields.add(new FloatField("x", this.scanner, this.printWriter));
        coordinatesFields.add(new IntegerField("y", this.scanner, this.printWriter));
        for (Field<?> field : coordinatesFields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }

        List<Field<?>> locationFields = new ArrayList<>();
        locationFields.add(new DoubleField("x", this.scanner, this.printWriter));
        locationFields.add(new IntegerField("y", this.scanner, this.printWriter));
        locationFields.add(new StringField("name", this.scanner, this.printWriter));
        for (Field<?> field : locationFields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }

        List<Field<?>> fields = new ArrayList<>();

        fields.add(new StringField("name", this.scanner, this.printWriter));
        fields.add(new ObjectField<Coordinates>("coordinates", coordinatesFields, this.scanner, this.printWriter));
        fields.add(new LongField("height", this.scanner, this.printWriter));
        fields.add(new StringField("passportID", this.scanner, this.printWriter));
        fields.add(new EnumField<>("eyeColor", Color.class, this.scanner, this.printWriter));
        fields.add(new EnumField<>("nationality", Country.class, this.scanner, this.printWriter));
        fields.add(new ObjectField<Location>("location", locationFields, this.scanner, this.printWriter));
        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }

        Form personForm = new Form(fields, this.printWriter);

        personForm.fillObjectWithValidatedUserInput(personToUpdate);
    }

    @Override
    public String getHelp() {
        return "Update an element with id=`id`. Syntax: " + this.getName() + " id {element}";
    }
}
