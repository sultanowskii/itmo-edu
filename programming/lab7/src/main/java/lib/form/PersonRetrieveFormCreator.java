package lib.form;

import lib.date.DateTimeFormatterBuilder;
import lib.form.field.*;
import lib.form.validation.ExclusiveBoundsValidator;
import lib.form.validation.NonEmptyStringValidator;
import lib.form.validation.NonNullValidator;
import lib.form.validation.StringLengthValidator;
import lib.schema.Color;
import lib.schema.Coordinates;
import lib.schema.Country;
import lib.schema.Location;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonRetrieveFormCreator {
    public static List<Field<?>> getPersonFields(PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();

        fields.add(getIDField(printWriter));
        fields.add(getNameField(printWriter));
        fields.add(getCoordinatesField(printWriter));
        fields.add(getCreationDateField(printWriter));
        fields.add(getHeightField(printWriter));
        fields.add(getPassportIDField(printWriter));
        fields.add(getEyeColorField(printWriter));
        fields.add(getNationalityField(printWriter));
        fields.add(getLocationField(printWriter));

        return fields;
    }

    public static ObjectField<Location> getLocationField(PrintWriter printWriter) {
        List<Field<?>> locationFields = getLocationFields(printWriter);
        return new ObjectField<>("location", locationFields, printWriter);
    }

    public static Field<Country> getNationalityField(PrintWriter printWriter) {
        return new EnumField<>("nationality", Country.class, printWriter)
                .addRawValueValidator(new NonNullValidator<>());
    }

    public static Field<Color> getEyeColorField(PrintWriter printWriter) {
        return new EnumField<>("eyeColor", Color.class, printWriter)
            .addRawValueValidator(new NonNullValidator<>());
    }

    public static Field<String> getPassportIDField(PrintWriter printWriter) {
        return new StringField("passportID", printWriter)
                .addValueValidator(new StringLengthValidator(0, 25))
                .addValueValidator(new NonEmptyStringValidator());
    }

    public static Field<Long> getHeightField(PrintWriter printWriter) {
        return new LongField("height", printWriter)
                .addValueValidator(ExclusiveBoundsValidator.newExclusiveLowerBoundValidator(0L))
                .addRawValueValidator(new NonNullValidator<>());
    }

    public static Field<ZonedDateTime> getCreationDateField(PrintWriter printWriter) {
        DateTimeFormatter formatter = DateTimeFormatterBuilder.getDateTimeFormatter();
        String pattern = DateTimeFormatterBuilder.getDateTimePattern();
        return new ZonedDateTimeField("creationDate", formatter, pattern, printWriter)
                .addRawValueValidator(new NonNullValidator<>());
    }

    public static ObjectField<Coordinates> getCoordinatesField(PrintWriter printWriter) {
        List<Field<?>> coordinatesFields = getCoordinatesFields(printWriter);
        return new ObjectField<>("coordinates", coordinatesFields, printWriter);
    }

    public static StringField getNameField(PrintWriter printWriter) {
        return new StringField("name", printWriter);
    }

    public static IntegerField getIDField(PrintWriter printWriter) {
        return new IntegerField("id", printWriter);
    }

    public static List<Field<?>> getLocationFields(PrintWriter printWriter) {
        List<Field<?>> locationFields = new ArrayList<>();
        locationFields.add(getLocationXField(printWriter));
        locationFields.add(getLocationYField(printWriter));
        locationFields.add(getLocationNameField(printWriter));

        return locationFields;
    }

    public static StringField getLocationNameField(PrintWriter printWriter) {
        return new StringField("name", printWriter);
    }

    public static IntegerField getLocationYField(PrintWriter printWriter) {
        return new IntegerField("y", printWriter);
    }

    public static DoubleField getLocationXField(PrintWriter printWriter) {
        return new DoubleField("x", printWriter);
    }

    public static List<Field<?>> getCoordinatesFields(PrintWriter printWriter) {
        List<Field<?>> coordinatesFields = new ArrayList<>();
        coordinatesFields.add(getCoordinatesXField(printWriter));
        coordinatesFields.add(getCoordinatesYField(printWriter));

        return coordinatesFields;
    }

    public static IntegerField getCoordinatesYField(PrintWriter printWriter) {
        return new IntegerField("y", printWriter);
    }

    public static FloatField getCoordinatesXField(PrintWriter printWriter) {
        return new FloatField("x", printWriter);
    }

    public static Form getForm(PrintWriter printWriter) {
        List<Field<?>> fields = getPersonFields(printWriter);

        return new Form(fields, printWriter);
    }
}
