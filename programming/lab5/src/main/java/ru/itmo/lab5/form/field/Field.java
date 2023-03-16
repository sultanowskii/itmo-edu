package ru.itmo.lab5.form.field;

import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.form.validation.Validator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Field representing a field of serializable/deserializable object
 * @param <T> Type of the field
 */
public abstract class Field<T> {
    protected String name;
    protected String rawValue;
    protected T value;
    protected boolean isParsed = false;
    protected List<Validator<String>> rawValueValidators;
    protected List<Validator<T>> valueValidators;
    protected Scanner scanner;
    protected PrintWriter printWriter;

    /**
     * Basic constructor
     * @param name Name of the field
     * @param scanner Global input (to get user input)
     * @param printWriter Global output (to print something to user)
     */
    public Field(String name, Scanner scanner, PrintWriter printWriter) {
        this.name = name;
        this.scanner = scanner;
        this.rawValueValidators = new ArrayList<>();
        this.valueValidators = new ArrayList<>();
        this.printWriter = printWriter;
    }

    /**
     * Constructor with validators
     * @param name Name of the field
     * @param rawValueValidators List of raw value validators
     * @param valueValidators List of value validators
     * @param scanner Global input (to get user input)
     * @param printWriter Global output (to print something to user)
     */
    public Field(String name, List<Validator<String>> rawValueValidators, List<Validator<T>> valueValidators, Scanner scanner, PrintWriter printWriter) {
        this.name = name;
        this.rawValueValidators = rawValueValidators;
        this.valueValidators = valueValidators;
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    public String getName() {
        return this.name;
    }

    protected String getOffset(int offsetSize) {
        return " ".repeat(offsetSize);
    }

    /**
     * Add value validator
     * @param newValidator Validator to be added
     * @return Current instance
     */
    public Field<T> addValueValidator(Validator<T> newValidator) {
        this.valueValidators.add(newValidator);
        return this;
    }

    /**
     * Add raw value validator
     * @param newValidator Validator to be added
     * @return Current instance
     */
    public Field<T> addRawValueValidator(Validator<String> newValidator) {
        this.rawValueValidators.add(newValidator);
        return this;
    }

    /**
     * Print information message about the field using printWriter
     */
    public abstract void printInfoMessage();

    /**
     * Get raw (unparsed) value from string
     * @param rawString String to be parsed
     * @return Raw value or null
     */
    public String getRawValue(String rawString) {
        StringTokenizer stringTokenizer = new StringTokenizer(rawString);
        if (stringTokenizer.hasMoreTokens()) {
            return stringTokenizer.nextToken();
        }
        return null;
    }

    /**
     * Get raw value from user input
     * @param offsetSize Indentation depth offset size
     */
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

    /**
     * Validate raw value (using current raw value validators)
     * @throws ValidationException If any validator failed
     */
    public void validateRawValue() throws ValidationException {
        for (Validator<String> validator : this.rawValueValidators) {
            validator.validate(this.rawValue);
        }
    }

    /**
     * Validate parsed value (using current value validators)
     * @throws ValidationException If any validator failed
     */
    public void validateParsedValue() throws ValidationException {
        for (Validator<T> validator : this.valueValidators) {
            validator.validate(this.value);
        }
        this.isParsed = true;
    }

    /**
     * Parse raw value (it has to be already set)
     */
    public abstract void parseRawValue();

    public T getValue() {
        if (this.isParsed)
            return this.value;
        return null;
    }

    /**
     * Get validated raw value from user input
     * @param offsetSize Indentation depth offset size
     */
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

    /**
     * Get parsed and validated value from user input (default way to get value of field from user)
     * @param offsetSize Indentation depth offset size
     */
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

    /**
     * Set parsed value to object
     * @param object Object which's field has to be set
     * @throws NoSuchFieldException If such field is not found on the object
     * @throws IllegalAccessException If this field is inaccessible on the object
     */
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

    /**
     * Get stringified value from object
     * @param object Object to get field value from
     * @param offsetSize Indentation depth offset size
     * @return Beautifed string representing value
     * @throws NoSuchFieldException If such field is not found on the object
     * @throws IllegalAccessException If this field is inaccessible on the object
     */
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

    /**
     * Parse and set value to object
     * @param object Object to set field value to
     * @param rawValue Raw value to be parsed
     * @throws NoSuchFieldException If such field is not found on the object
     * @throws IllegalAccessException If this field is inaccessible on the object
     */
    public void parseAndSetValue(Object object, String rawValue) throws NoSuchFieldException, IllegalAccessException {
        this.parseAndGetValue(rawValue);
        this.setValueToObject(object);
    }

    /**
     * Parse and get value
     * @param rawValue Raw value to be parsed
     * @return Parsed value
     */
    public T parseAndGetValue(String rawValue) {
        try {
            this.setRawValue(rawValue);
            this.validateRawValue();
            this.parseRawValue();
            this.validateParsedValue();
        } catch (ValidationException e) {
            throw new ValidationException("Validation error on field `" + this.name + "`: " + e.getMessage());
        } catch (NullPointerException e) {
            throw new ValidationException("Validation error on field `" + this.name + "`: " + "This field is required.");
        }
        return this.value;
    }
}
