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
    public static Field<Double> getLocationXField(Scanner scanner, PrintWriter printWriter) {
        return new DoubleField("x", scanner, printWriter);
    }

    public static IntegerField getLocationYField(Scanner scanner, PrintWriter printWriter) {
        return new IntegerField("y", scanner, printWriter);
    }

    public static StringField getLocationNameField(Scanner scanner, PrintWriter printWriter) {
        return new StringField("name", scanner, printWriter);
    }

    public static List<Field<?>> getLocationFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();
        fields.add(getLocationXField(scanner, printWriter));
        fields.add(getLocationYField(scanner, printWriter));
        fields.add(getLocationNameField(scanner, printWriter));
        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }
        return fields;
    }

    public static Field<Integer> getCoordinatesYField(Scanner scanner, PrintWriter printWriter) {
        return new IntegerField("y", scanner, printWriter)
                .addValueValidator(InclusiveBoundsValidator.newInclusiveUpperBoundValidator(897));
    }

    public static Field<Float> getCoordinatesXField(Scanner scanner, PrintWriter printWriter) {
        return new FloatField("x", scanner, printWriter)
                .addValueValidator(ExclusiveBoundsValidator.newExclusiveLowerBoundValidator(-527f));
    }

    public static List<Field<?>> getCoordinatesFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();
        fields.add(getCoordinatesXField(scanner, printWriter));
        fields.add(getCoordinatesYField(scanner, printWriter));
        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }
        return fields;
    }

    public static Field<String> getNameField(Scanner scanner, PrintWriter printWriter) {
        return new StringField("name", scanner, printWriter)
                .addValueValidator(new NonEmptyStringValidator())
                .addValueValidator(new NonNullValidator<>());
    }

    public static EnumField<Country> getNationalityField(Scanner scanner, PrintWriter printWriter) {
        return new EnumField<>("nationality", Country.class, scanner, printWriter);
    }

    public static EnumField<Color> getEyeColorField(Scanner scanner, PrintWriter printWriter) {
        return new EnumField<>("eyeColor", Color.class, scanner, printWriter);
    }

    public static Field<String> getPassportIDField(Scanner scanner, PrintWriter printWriter) {
        return new StringField("passportID", scanner, printWriter)
                .addValueValidator(new StringLengthValidator(0, 25))
                .addValueValidator(new NonEmptyStringValidator());
    }

    public static Field<Long> getHeightField(Scanner scanner, PrintWriter printWriter) {
        return new LongField("height", scanner, printWriter)
                .addValueValidator(ExclusiveBoundsValidator.newExclusiveLowerBoundValidator(0L));
    }

    private static List<Field<?>> getPersonFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();

        fields.add(getNameField(scanner, printWriter));

        List<Field<?>> coordinatesFields = getCoordinatesFields(scanner, printWriter);
        fields.add(new ObjectField<Coordinates>("coordinates", coordinatesFields, scanner, printWriter));

        fields.add(getHeightField(scanner, printWriter));
        fields.add(getPassportIDField(scanner, printWriter));
        fields.add(getEyeColorField(scanner, printWriter));
        fields.add(getNationalityField(scanner, printWriter));

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
