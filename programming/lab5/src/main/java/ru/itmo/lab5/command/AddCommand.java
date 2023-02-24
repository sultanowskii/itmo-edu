package ru.itmo.lab5.command;

import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.form.validation.NonNullValidator;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import java.io.PrintWriter;
import java.util.*;

public class AddCommand extends Command {

    public AddCommand(Scanner scanner, PrintWriter printWriter) {
        super("add", scanner, printWriter);
    }

    @Override
    public void exec(List<String> args, Context context) {
        PersonManager personManager = context.getPersonManager();

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

        Person newPerson = new Person();

        personForm.fillObjectWithValidatedUserInput(newPerson);

        personManager.add(newPerson);
    }

    @Override
    public String getHelp() {
        return "Add new element to the collection. Syntax: " + this.getName() + " {element}";
    }
}
