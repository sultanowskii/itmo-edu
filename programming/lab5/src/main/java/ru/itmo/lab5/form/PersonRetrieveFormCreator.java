package ru.itmo.lab5.form;

import ru.itmo.lab5.date.DateTimeFormatterBuilder;
import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.schema.Color;
import ru.itmo.lab5.schema.Coordinates;
import ru.itmo.lab5.schema.Country;
import ru.itmo.lab5.schema.Location;

import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonRetrieveFormCreator {
    public static List<Field<?>> getPersonFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();

        fields.add(getIDField(scanner, printWriter));
        fields.add(getNameField(scanner, printWriter));
        fields.add(getCoordinatesField(scanner, printWriter));
        fields.add(getCreationDateField(scanner, printWriter));
        fields.add(getHeightField(scanner, printWriter));
        fields.add(getPassportIDField(scanner, printWriter));
        fields.add(getEyeColorField(scanner, printWriter));
        fields.add(getNationalityField(scanner, printWriter));
        fields.add(getLocationField(scanner, printWriter));

        return fields;
    }

    public static ObjectField<Location> getLocationField(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> locationFields = getLocationFields(scanner, printWriter);
        return new ObjectField<>("location", locationFields, scanner, printWriter);
    }

    public static EnumField<Country> getNationalityField(Scanner scanner, PrintWriter printWriter) {
        return new EnumField<>("nationality", Country.class, scanner, printWriter);
    }

    public static EnumField<Color> getEyeColorField(Scanner scanner, PrintWriter printWriter) {
        return new EnumField<>("eyeColor", Color.class, scanner, printWriter);
    }

    public static StringField getPassportIDField(Scanner scanner, PrintWriter printWriter) {
        return new StringField("passportID", scanner, printWriter);
    }

    public static LongField getHeightField(Scanner scanner, PrintWriter printWriter) {
        return new LongField("height", scanner, printWriter);
    }

    public static ZonedDateTimeField getCreationDateField(Scanner scanner, PrintWriter printWriter) {
        DateTimeFormatter formatter = DateTimeFormatterBuilder.getDateTimeFormatter();
        String pattern = DateTimeFormatterBuilder.getDateTimePattern();
        return new ZonedDateTimeField("creationDate", formatter, pattern, scanner, printWriter);
    }

    public static ObjectField<Coordinates> getCoordinatesField(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> coordinatesFields = getCoordinatesFields(scanner, printWriter);
        return new ObjectField<>("coordinates", coordinatesFields, scanner, printWriter);
    }

    public static StringField getNameField(Scanner scanner, PrintWriter printWriter) {
        return new StringField("name", scanner, printWriter);
    }

    public static IntegerField getIDField(Scanner scanner, PrintWriter printWriter) {
        return new IntegerField("id", scanner, printWriter);
    }

    public static List<Field<?>> getLocationFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> locationFields = new ArrayList<>();
        locationFields.add(getLocationXField(scanner, printWriter));
        locationFields.add(getLocationYField(scanner, printWriter));
        locationFields.add(getLocationNameField(scanner, printWriter));

        return locationFields;
    }

    public static StringField getLocationNameField(Scanner scanner, PrintWriter printWriter) {
        return new StringField("name", scanner, printWriter);
    }

    public static IntegerField getLocationYField(Scanner scanner, PrintWriter printWriter) {
        return new IntegerField("y", scanner, printWriter);
    }

    public static DoubleField getLocationXField(Scanner scanner, PrintWriter printWriter) {
        return new DoubleField("x", scanner, printWriter);
    }

    public static List<Field<?>> getCoordinatesFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> coordinatesFields = new ArrayList<>();
        coordinatesFields.add(getCoordinatesXField(scanner, printWriter));
        coordinatesFields.add(getCoordinatesYField(scanner, printWriter));

        return coordinatesFields;
    }

    public static IntegerField getCoordinatesYField(Scanner scanner, PrintWriter printWriter) {
        return new IntegerField("y", scanner, printWriter);
    }

    public static FloatField getCoordinatesXField(Scanner scanner, PrintWriter printWriter) {
        return new FloatField("x", scanner, printWriter);
    }

    public static Form getForm(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = getPersonFields(scanner, printWriter);

        return new Form(fields, printWriter);
    }
}
