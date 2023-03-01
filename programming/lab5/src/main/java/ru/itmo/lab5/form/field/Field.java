package ru.itmo.lab5.form.field;

import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.form.validation.Validator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public abstract class Field<T> {
    protected String name;
    protected String rawValue;
    protected T value;
    protected boolean isParsed = false;
    protected List<Validator<String>> rawValueValidators;
    protected List<Validator<T>> valueValidators;
    protected Scanner scanner;
    protected PrintWriter printWriter;

    public Field(String name, Scanner scanner, PrintWriter printWriter) {
        this.name = name;
        this.scanner = scanner;
        this.rawValueValidators = new ArrayList<>();
        this.valueValidators = new ArrayList<>();
        this.printWriter = printWriter;
    }

    public Field(String name, List<Validator<String>> rawValueValidators, List<Validator<T>> valueValidators, Scanner scanner, PrintWriter printWriter) {
        this.name = name;
        this.rawValueValidators = rawValueValidators;
        this.valueValidators = valueValidators;
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    protected String getOffset(int offsetSize) {
        return " ".repeat(offsetSize);
    }

    public Field<T> addValueValidator(Validator<T> newValidator) {
        this.valueValidators.add(newValidator);
        return this;
    }

    public Field<T> addRawValueValidator(Validator<String> newValidator) {
        this.rawValueValidators.add(newValidator);
        return this;
    }

    public abstract void printInfoMessage();

    public String getRawValue(String rawString) {
        StringTokenizer stringTokenizer = new StringTokenizer(rawString);
        if (stringTokenizer.hasMoreTokens()) {
            return stringTokenizer.nextToken();
        }
        return null;
    }

    public void getRawValueFromUser(int offsetSize) {
        printWriter.print(" ".repeat(offsetSize));
        this.printInfoMessage();
        this.rawValue = getRawValue(scanner.nextLine());
        this.isParsed = false;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
        this.isParsed = false;
    }

    public void validateRawValue() throws ValidationException {
        for (Validator<String> validator : this.rawValueValidators) {
            validator.validate(this.rawValue);
        }
    }

    public void validateParsedValue() throws ValidationException {
        for (Validator<T> validator : this.valueValidators) {
            validator.validate(this.value);
        }
        this.isParsed = true;
    }

    public abstract void parseRawValue();

    public T getValue() {
        if (this.isParsed)
            return this.value;
        return null;
    }

    protected void getValidatedRawValueFromUserInput(int offsetSize) {
        do {
            this.getRawValueFromUser(offsetSize);
            try {
                this.validateRawValue();
                break;
            } catch (ValidationException e) {
                this.printWriter.println(e.getMessage());
            }
        } while (true);
    }

    public void getParsedAndValidatedValueFromUserInput(int offsetSize) {
        do {
            this.getValidatedRawValueFromUserInput(offsetSize);
            this.parseRawValue();
            try {
                this.validateParsedValue();
                break;
            } catch (ValidationException e) {
                this.printWriter.println(e.getMessage());
            }
        } while (true);
    }

    public void setValueToObject(Object object) throws NoSuchFieldException, IllegalAccessException {
        if (!this.isParsed)
            return;

        java.lang.reflect.Field fieldToChange = null;
        try {
            fieldToChange = object.getClass().getDeclaredField(this.name);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException("Field `" + this.name +"` not found.");
        }

        fieldToChange.setAccessible(true);
        fieldToChange.set(object, this.value);
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
}
