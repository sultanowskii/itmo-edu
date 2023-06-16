package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.*;

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
    protected PrintWriter printWriter;

    /**
     * Basic constructor
     * @param name Name of the field
     * @param printWriter Global output (to print something to user)
     */
    public Field(String name, PrintWriter printWriter) {
        this.name = name;
        this.rawValueValidators = new ArrayList<>();
        this.valueValidators = new ArrayList<>();
        this.printWriter = printWriter;
    }

    /**
     * Constructor with validators
     * @param name Name of the field
     * @param rawValueValidators List of raw value validators
     * @param valueValidators List of value validators
     * @param printWriter Global output (to print something to user)
     */
    public Field(String name, List<Validator<String>> rawValueValidators, List<Validator<T>> valueValidators, PrintWriter printWriter) {
        this.name = name;
        this.rawValueValidators = rawValueValidators;
        this.valueValidators = valueValidators;
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
    public abstract void printInfoMessage(ResourceBundle messageBundle);

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
    public void getRawValueFromUser(Scanner scanner, int offsetSize, ResourceBundle messageBundle) {
        printWriter.print(" ".repeat(offsetSize));
        this.printInfoMessage(messageBundle);
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
    public void validateRawValue(ResourceBundle messageBundle) throws ValidationException {
        for (Validator<String> validator : this.rawValueValidators) {
            validator.validate(this.rawValue, messageBundle);
        }
    }

    /**
     * Validate parsed value (using current value validators)
     * @throws ValidationException If any validator failed
     */
    public void validateParsedValue(ResourceBundle messageBundle) throws ValidationException {
        for (Validator<T> validator : this.valueValidators) {
            validator.validate(this.value, messageBundle);
        }
        this.isParsed = true;
    }

    /**
     * Parse raw value (it has to be already set)
     */
    public abstract void parseRawValue(ResourceBundle messageBundle);

    public T getValue() {
        if (this.isParsed)
            return this.value;
        return null;
    }

    /**
     * Get validated raw value from user input
     * @param offsetSize Indentation depth offset size
     */
    protected void getValidatedRawValueFromUserInput(Scanner scanner, int offsetSize, ResourceBundle messageBundle) {
        do {
            this.getRawValueFromUser(scanner, offsetSize, messageBundle);
            try {
                this.validateRawValue(messageBundle);
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
    public void getParsedAndValidatedValueFromUserInput(Scanner scanner, int offsetSize, ResourceBundle messageBundle) {
        do {
            this.getValidatedRawValueFromUserInput(scanner, offsetSize, messageBundle);
            this.parseRawValue(messageBundle);
            try {
                this.validateParsedValue(messageBundle);
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
    public void setValueToObject(Object object, ResourceBundle messageBundle) throws NoSuchFieldException, IllegalAccessException {
        if (!this.isParsed)
            return;

        java.lang.reflect.Field fieldToChange = null;
        try {
            fieldToChange = object.getClass().getDeclaredField(this.name);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(messageBundle.getString("error.field.notFound") + ": `" + this.name + "`");
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

    /**
     * Parse and set value to object
     * @param object Object to set field value to
     * @param rawValue Raw value to be parsed
     * @throws NoSuchFieldException If such field is not found on the object
     * @throws IllegalAccessException If this field is inaccessible on the object
     */
    public void parseAndSetValue(Object object, String rawValue, ResourceBundle messageBundle) throws NoSuchFieldException, IllegalAccessException {
        this.parseAndGetValue(rawValue, messageBundle);
        this.setValueToObject(object, messageBundle);
    }

    /**
     * Parse and get value
     * @param rawValue Raw value to be parsed
     * @return Parsed value
     */
    public T parseAndGetValue(String rawValue, ResourceBundle messageBundle) {
        try {
            this.setRawValue(rawValue.trim());
            this.validateRawValue(messageBundle);
            this.parseRawValue(messageBundle);
            this.validateParsedValue(messageBundle);
        } catch (ValidationException e) {
            throw new ValidationException(messageBundle.getString("error.validation") + ": `" + this.name + "`: " + e.getMessage());
        } catch (NullPointerException e) {
            throw new ValidationException(messageBundle.getString("error.field.required") + ": `" + this.name + "`");
        }
        return this.value;
    }
}
