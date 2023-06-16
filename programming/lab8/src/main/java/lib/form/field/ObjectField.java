package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ObjectField<T> extends Field<T> {
    List<Field<?>> fields;

    public ObjectField(String name, List<Field<?>> fields, PrintWriter printWriter) {
        super(name, printWriter);
        this.fields = fields;
    }

    public ObjectField(String name, List<Field<?>> fields, List<Validator<String>> rawValueValidators, List<Validator<T>> valueValidators, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, printWriter);
        this.fields = fields;
    }

    void addField(Field<?> newField) {
        this.fields.add(newField);
    }

    @Override
    public void printInfoMessage(ResourceBundle messageBundle) {
        this.printWriter.println(messageBundle.getString("message.fillFieldsOf") + " `" + this.name + "`:");
    }

    @Override
    public void getRawValueFromUser(Scanner scanner, int offsetSize, ResourceBundle messageBundle) {
        this.printInfoMessage(messageBundle);
        for (Field<?> field : this.fields) {
            field.getRawValueFromUser(scanner, offsetSize, messageBundle);
        }
    }

    @Override
    public void validateRawValue(ResourceBundle messageBundle) throws ValidationException {
        super.validateRawValue(messageBundle);
        for (Field<?> field : this.fields) {
            field.validateRawValue(messageBundle);
        }
    }

    @Override
    public void parseRawValue(ResourceBundle messageBundle) {
        for (Field<?> field : this.fields) {
            field.parseRawValue(messageBundle);
        }
    }

    @Override
    public void validateParsedValue(ResourceBundle messageBundle) throws ValidationException {
        for (Field<?> field : this.fields) {
            field.validateParsedValue(messageBundle);
        }
        this.isParsed = true;
    }

    @Override
    protected void getValidatedRawValueFromUserInput(Scanner scanner, int offsetSize, ResourceBundle messageBundle) {
        this.printInfoMessage(messageBundle);
        for (Field<?> field : this.fields) {
            do {
                field.getRawValueFromUser(scanner, offsetSize, messageBundle);
                try {
                    field.validateRawValue(messageBundle);
                    break;
                } catch (ValidationException e) {
                    this.printWriter.println(e.getMessage());
                }
            } while (true);
        }
    }

    @Override
    public void getParsedAndValidatedValueFromUserInput(Scanner scanner, int offsetSize, ResourceBundle messageBundle) {
        this.printInfoMessage(messageBundle);
        for (Field<?> field : this.fields) {
            field.getParsedAndValidatedValueFromUserInput(scanner, offsetSize + 1, messageBundle);
        }
        this.isParsed = true;
    }

    @Override
    public void setValueToObject(Object object, ResourceBundle messageBundle) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field fieldToGet = null;
        try {
            fieldToGet = object.getClass().getDeclaredField(this.name);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(messageBundle.getString("error.field.notFound") + ": `" + this.name + "`");
        }
        fieldToGet.setAccessible(true);
        Object nestedObject = fieldToGet.get(object);

        for (Field<?> field : this.fields) {
            field.setValueToObject(nestedObject, messageBundle);
        }
        this.value = (T)nestedObject;
        super.setValueToObject(object, messageBundle);
    }

    public String getStringifiedValueFromObject(Object object, int offsetSize, ResourceBundle messageBundle) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder(getOffset(offsetSize) + this.name + ":\n");

        java.lang.reflect.Field fieldToGet = null;
        try {
            fieldToGet = object.getClass().getDeclaredField(this.name.trim());
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(messageBundle.getString("error.field.notFound") + ": `" + this.name + "`");
        }

        fieldToGet.setAccessible(true);
        Object nestedObject = fieldToGet.get(object);

        for (Field<?> field : this.fields) {
            stringBuilder.append(field.getStringifiedValueFromObject(nestedObject, offsetSize + 1, messageBundle));
        }

        return stringBuilder.toString();
    }
}
