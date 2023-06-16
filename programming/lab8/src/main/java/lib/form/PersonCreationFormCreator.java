package lib.form;

import lib.form.field.*;
import lib.form.validation.*;
import lib.schema.Color;
import lib.schema.Coordinates;
import lib.schema.Country;
import lib.schema.Location;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonCreationFormCreator {
    public static Field<Double> getLocationXField(PrintWriter printWriter) {
        return new DoubleField("x", printWriter);
    }

    public static IntegerField getLocationYField(PrintWriter printWriter) {
        return new IntegerField("y", printWriter);
    }

    public static StringField getLocationNameField(PrintWriter printWriter) {
        return new StringField("name", printWriter);
    }

    public static List<Field<?>> getLocationFields(PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();
        fields.add(getLocationXField(printWriter));
        fields.add(getLocationYField(printWriter));
        fields.add(getLocationNameField(printWriter));
        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }
        return fields;
    }

    public static Field<Integer> getCoordinatesYField(PrintWriter printWriter) {
        return new IntegerField("y", printWriter)
                .addValueValidator(InclusiveBoundsValidator.newInclusiveUpperBoundValidator(897));
    }

    public static Field<Float> getCoordinatesXField(PrintWriter printWriter) {
        return new FloatField("x", printWriter)
                .addValueValidator(ExclusiveBoundsValidator.newExclusiveLowerBoundValidator(-527f));
    }

    public static List<Field<?>> getCoordinatesFields(PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();
        fields.add(getCoordinatesXField(printWriter));
        fields.add(getCoordinatesYField(printWriter));
        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }
        return fields;
    }

    public static Field<String> getNameField(PrintWriter printWriter) {
        return new StringField("name", printWriter)
                .addValueValidator(new NonEmptyStringValidator())
                .addValueValidator(new NonNullValidator<>());
    }

    public static EnumField<Country> getNationalityField(PrintWriter printWriter) {
        return new EnumField<>("nationality", Country.class, printWriter);
    }

    public static EnumField<Color> getEyeColorField(PrintWriter printWriter) {
        return new EnumField<>("eyeColor", Color.class, printWriter);
    }

    public static Field<String> getPassportIDField(PrintWriter printWriter) {
        return new StringField("passportID", printWriter)
                .addValueValidator(new StringLengthValidator(0, 25))
                .addValueValidator(new NonEmptyStringValidator());
    }

    public static Field<Long> getHeightField(PrintWriter printWriter) {
        return new LongField("height", printWriter)
                .addValueValidator(ExclusiveBoundsValidator.newExclusiveLowerBoundValidator(0L));
    }

    private static List<Field<?>> getPersonFields(PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();

        fields.add(getNameField(printWriter));

        List<Field<?>> coordinatesFields = getCoordinatesFields(printWriter);
        fields.add(new ObjectField<Coordinates>("coordinates", coordinatesFields, printWriter));

        fields.add(getHeightField(printWriter));
        fields.add(getPassportIDField(printWriter));
        fields.add(getEyeColorField(printWriter));
        fields.add(getNationalityField(printWriter));

        List<Field<?>> locationFields = getLocationFields(printWriter);
        fields.add(new ObjectField<Location>("location", locationFields, printWriter));

        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }

        return fields;
    }

    public static Form getForm(PrintWriter printWriter) {
        List<Field<?>> fields = getPersonFields(printWriter);
        return new Form(fields, printWriter);
    }

    public static Form getLocationForm(PrintWriter printWriter) {
        List<Field<?>> fields = getLocationFields(printWriter);
        return new Form(fields, printWriter);
    }
}
