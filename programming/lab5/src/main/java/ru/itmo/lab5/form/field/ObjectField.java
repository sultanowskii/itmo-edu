package ru.itmo.lab5.form.field;

import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ObjectField<T> extends Field<T> {
    List<Field<?>> fields;

    public ObjectField(String name, List<Field<?>> fields, Scanner scanner, PrintWriter printWriter) {
        super(name, scanner, printWriter);
        this.fields = fields;
    }

    public ObjectField(String name, List<Field<?>> fields, List<Validator<String>> rawValueValidators, List<Validator<T>> valueValidators, Scanner scanner, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, scanner, printWriter);
        this.fields = fields;
    }

    void addField(Field<?> newField) {
        this.fields.add(newField);
    }

    @Override
    public void printInfoMessage() {
        this.printWriter.println("Fill the following fields of `" + this.name + "`:");
    }

    @Override
    public void getRawValueFromUser(int offsetSize) {
        this.printInfoMessage();
        for (Field<?> field : this.fields) {
            field.getRawValueFromUser(offsetSize);
        }
    }

    @Override
    public void validateRawValue() throws ValidationException {
        super.validateRawValue();
        for (Field<?> field : this.fields) {
            field.validateRawValue();
        }
    }

    @Override
    public void parseRawValue() {
        for (Field<?> field : this.fields) {
            field.parseRawValue();
        }
    }

    @Override
    public void validateParsedValue() throws ValidationException {
        for (Field<?> field : this.fields) {
            field.validateParsedValue();
        }
        this.isParsed = true;
    }

    @Override
    protected void getValidatedRawValueFromUserInput(int offsetSize) {
        this.printInfoMessage();
        for (Field<?> field : this.fields) {
            do {
                field.getRawValueFromUser(offsetSize);
                try {
                    field.validateRawValue();
                    break;
                } catch (ValidationException e) {
                    this.printWriter.println(e.getMessage());
                }
            } while (true);
        }
    }

    @Override
    public void getParsedAndValidatedValueFromUserInput(int offsetSize) {
        this.printInfoMessage();
        for (Field<?> field : this.fields) {
            field.getParsedAndValidatedValueFromUserInput(offsetSize + 1);
        }
        this.isParsed = true;
    }

    @Override
    public void setValueToObject(Object object) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field fieldToGet = null;
        try {
            fieldToGet = object.getClass().getDeclaredField(this.name);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException("Field `" + this.name +"` not found.");
        }
        fieldToGet.setAccessible(true);
        Object nestedObject = fieldToGet.get(object);

        for (Field<?> field : this.fields) {
            field.setValueToObject(nestedObject);
        }
        this.value = (T)nestedObject;
        super.setValueToObject(object);
    }

    public String getStringifiedValueFromObject(Object object, int offsetSize) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder(getOffset(offsetSize) + this.name + ":\n");

        java.lang.reflect.Field fieldToGet = null;
        try {
            fieldToGet = object.getClass().getDeclaredField(this.name.trim());
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException("Field `" + this.name + "` not found.");
        }
        fieldToGet.setAccessible(true);
        Object nestedObject = fieldToGet.get(object);

        for (Field<?> field : this.fields) {
            stringBuilder.append(field.getStringifiedValueFromObject(nestedObject, offsetSize + 1));
        }

        return stringBuilder.toString();
    }
}
