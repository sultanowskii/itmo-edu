package ru.itmo.lab5.form;

import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.schema.Color;
import ru.itmo.lab5.schema.Coordinates;
import ru.itmo.lab5.schema.Country;
import ru.itmo.lab5.schema.Location;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonRetrieveFormCreator {
    private static List<Field<?>> getPersonFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> locationFields = getLocationFields(scanner, printWriter);
        List<Field<?>> coordinatesFields = getCoordinatesFields(scanner, printWriter);
        List<Field<?>> fields = new ArrayList<>();

        String pattern = "dd/MM/yyyy hh:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        fields.add(new IntegerField("id", scanner, printWriter));
        fields.add(new StringField("name", scanner, printWriter));
        fields.add(new ObjectField<Coordinates>("coordinates", coordinatesFields, scanner, printWriter));
        fields.add(new ZonedDateTimeField("creationDate", formatter, pattern, scanner, printWriter));
        fields.add(new LongField("height", scanner, printWriter));
        fields.add(new StringField("passportID", scanner, printWriter));
        fields.add(new EnumField<Color>("eyeColor", Color.class, scanner, printWriter));
        fields.add(new EnumField<Country>("nationality", Country.class, scanner, printWriter));
        fields.add(new ObjectField<Location>("location", locationFields, scanner, printWriter));

        return fields;
    }

    private static List<Field<?>> getLocationFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> locationFields = new ArrayList<>();
        locationFields.add(new DoubleField("x", scanner, printWriter));
        locationFields.add(new IntegerField("y", scanner, printWriter));
        locationFields.add(new StringField("name", scanner, printWriter));

        return locationFields;
    }

    private static List<Field<?>> getCoordinatesFields(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> coordinatesFields = new ArrayList<>();
        coordinatesFields.add(new FloatField("x", scanner, printWriter));
        coordinatesFields.add(new IntegerField("y", scanner, printWriter));

        return coordinatesFields;
    }

    public static Form getForm(Scanner scanner, PrintWriter printWriter) {
        List<Field<?>> fields = getPersonFields(scanner, printWriter);

        return new Form(fields, printWriter);
    }
}
