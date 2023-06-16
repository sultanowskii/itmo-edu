package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ZonedDateTimeField extends Field<ZonedDateTime> {
    protected DateTimeFormatter formatter;
    protected String pattern;

    public ZonedDateTimeField(
            String name,
            DateTimeFormatter formatter,
            String formatPattern,
            PrintWriter printWriter
    ) {
        super(name, printWriter);
        this.formatter = formatter;
        this.pattern = formatPattern;
    }

    public ZonedDateTimeField(
            String name,
            DateTimeFormatter formatter,
            String formatPattern,
            List<Validator<String>> rawValueValidators,
            List<Validator<ZonedDateTime>> valueValidators,
            PrintWriter printWriter
    ) {
        super(name, rawValueValidators, valueValidators, printWriter);
        this.formatter = formatter;
        this.pattern = formatPattern;
    }

    public String getPattern() {
        return pattern;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    @Override
    public void printInfoMessage(ResourceBundle messageBundle) {
        this.printWriter.print(
            messageBundle.getString("message.enter")
            + " `" + this.name
            + "` (" + messageBundle.getString("type.datetime") + " " + messageBundle.getString("message.inThisFormat") + ": " + this.pattern + "): "
        );
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue(ResourceBundle messageBundle) {
        this.value = ZonedDateTime.parse(this.rawValue, this.formatter);
    }

    public String getStringifiedValueFromObject(Object object, int offsetSize, ResourceBundle messageBundle) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field fieldToGet = null;

        try {
            fieldToGet = object.getClass().getDeclaredField(this.name);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(messageBundle.getString("error.field.notFound") + ": `" + this.name + "`");
        }
        fieldToGet.setAccessible(true);

        return getOffset(offsetSize) + this.name + ": " + fieldToGet.get(object) + "\n";
    }

    @Override
    public void validateRawValue(ResourceBundle messageBundle) throws ValidationException {
        super.validateRawValue(messageBundle);
        try {
            ZonedDateTime.parse(this.rawValue.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new ValidationException(
                messageBundle.getString("message.pleaseEnter")
                + " `" + this.name
                + "` (" + messageBundle.getString("type.datetime") + " " + messageBundle.getString("message.inThisFormat") + ": " + this.pattern + "): "
            );
        }
    }
}
