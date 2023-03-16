package ru.itmo.lab5.form.field;

import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.form.validation.Validator;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ZonedDateTimeField extends Field<ZonedDateTime> {
    protected DateTimeFormatter formatter;
    protected String pattern;

    public ZonedDateTimeField(
            String name,
            DateTimeFormatter formatter,
            String formatPattern,
            Scanner scanner,
            PrintWriter printWriter
    ) {
        super(name, scanner, printWriter);
        this.formatter = formatter;
        this.pattern = formatPattern;
    }

    public ZonedDateTimeField(
            String name,
            DateTimeFormatter formatter,
            String formatPattern,
            List<Validator<String>> rawValueValidators,
            List<Validator<ZonedDateTime>> valueValidators,
            Scanner scanner,
            PrintWriter printWriter
    ) {
        super(name, rawValueValidators, valueValidators, scanner, printWriter);
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
    public void printInfoMessage() {
        this.printWriter.print("Enter `" + this.name + "` (datetime in this format: " + this.pattern + "): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue() {
        this.value = ZonedDateTime.parse(this.rawValue, this.formatter);
    }

    public String getStringifiedValueFromObject(Object object, int offsetSize) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field fieldToGet = null;

        try {
            fieldToGet = object.getClass().getDeclaredField(this.name);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException("Field `" + this.name +"` not found.");
        }
        fieldToGet.setAccessible(true);

        return getOffset(offsetSize) + this.name + ": " + fieldToGet.get(object) + "\n";
    }

    @Override
    public void validateRawValue() throws ValidationException {
        super.validateRawValue();
        try {
            ZonedDateTime.parse(this.rawValue.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Please enter datetime in the following format: " + this.pattern + ".");
        }
    }
}
