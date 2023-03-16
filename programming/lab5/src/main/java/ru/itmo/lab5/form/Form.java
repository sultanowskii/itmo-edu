package ru.itmo.lab5.form;

import ru.itmo.lab5.form.field.Field;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Form. Contains a list of fields (of some object). Used for serialization/deserialization
 */
public class Form {
    protected List<Field<?>> fields;
    protected Scanner scanner;
    protected PrintWriter printWriter;

    /**
     * Default constructor
     * @param printWriter Global output (to print something to user)
     */
    public Form(PrintWriter printWriter) {
        this.fields = new ArrayList<>();
        this.printWriter = printWriter;
    }

    /**
     * Constructor with the list of fields
     * @param fields List of field
     * @param printWriter Global output (to print something to user)
     */
    public Form(List<Field<?>> fields, PrintWriter printWriter) {
        this.fields = fields;
        this.printWriter = printWriter;
    }

    /**
     * Add new field to the form
     * @param newField Field ot be added
     */
    void addField(Field<?> newField) {
        this.fields.add(newField);
    }

    /**
     * Get list of fields
     * @return List of fields
     */
    public List<Field<?>> getFields() {
        return this.fields;
    }

    /**
     * Fill object's specified fields with validated user input. All inner objects has to be pre-created.
     * @param object Object to be filled.
     */
    public void fillObjectWithValidatedUserInput(Object object) {
        for (Field<?> field : this.fields) {
            field.getParsedAndValidatedValueFromUserInput(0);
            try {
                field.setValueToObject(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                this.printWriter.println("Error occurred. This is not your fault. Error detail for developers: " + e + "");
            }
        }
    }

    /**
     * Get stringified value from object.
     * @param object Object to be stringified
     * @param offsetLength Indentation depth offset size
     * @return String representation of the object
     */
    public String getStringifiedValueFromObject(Object object, int offsetLength) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field<?> field : this.fields) {
            try {
                stringBuilder.append(field.getStringifiedValueFromObject(object, offsetLength + 1));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                this.printWriter.println("Error occurred. This is not your fault. Error detail for developers: " + e + "");
            }
        }
        return stringBuilder.toString();
    }
}
