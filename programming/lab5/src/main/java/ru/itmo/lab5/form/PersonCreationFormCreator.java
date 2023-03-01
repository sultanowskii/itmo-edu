package ru.itmo.lab5.form;

import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.form.validation.*;
import ru.itmo.lab5.schema.Color;
import ru.itmo.lab5.schema.Coordinates;
import ru.itmo.lab5.schema.Country;
import ru.itmo.lab5.schema.Location;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonCreationFormCreator {
    private static List<Field<?>> getLocationFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();
        fields.add(new DoubleField("x", scanner, printWriter));
        fields.add(new IntegerField("y", scanner, printWriter));
        fields.add(new StringField("name", scanner, printWriter));
        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }
        return fields;
    }

    private static List<Field<?>> getCoordinatesFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();
        fields.add(
            new FloatField("x", scanner, printWriter)
            .addValueValidator(ExclusiveBoundsValidator.newExclusiveLowerBoundValidator(-527f))
        );
        fields.add(
            new IntegerField("y", scanner, printWriter)
            .addValueValidator(InclusiveBoundsValidator.newInclusiveUpperBoundValidator(897))
        );
        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }
        return fields;
    }

    private static List<Field<?>> getPersonFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();

        fields.add(
            new StringField("name", scanner, printWriter)
            .addValueValidator(new NonEmptyStringValidator())
        );

        List<Field<?>> coordinatesFields = getCoordinatesFields(scanner, printWriter);
        fields.add(new ObjectField<Coordinates>("coordinates", coordinatesFields, scanner, printWriter));

        fields.add(
            new LongField("height", scanner, printWriter)
            .addValueValidator(ExclusiveBoundsValidator.newExclusiveLowerBoundValidator(0L))
        );
        fields.add(
            new StringField("passportID", scanner, printWriter)
            .addValueValidator(new StringLengthValidator(0, 25))
            .addValueValidator(new NonEmptyStringValidator())
        );
        fields.add(new EnumField<>("eyeColor", Color.class, scanner, printWriter));
        fields.add(new EnumField<>("nationality", Country.class, scanner, printWriter));

        List<Field<?>> locationFields = getLocationFields(scanner, printWriter);
        fields.add(new ObjectField<Location>("location", locationFields, scanner, printWriter));

        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }

        return fields;
    }
    
    public static Form getForm(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = getPersonFields(scanner, printWriter);
        return new Form(fields, printWriter);
    }

    public static Form getLocationForm(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = getLocationFields(scanner, printWriter);
        return new Form(fields, printWriter);
    }
}
