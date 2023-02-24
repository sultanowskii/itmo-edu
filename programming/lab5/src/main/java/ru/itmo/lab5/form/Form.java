package ru.itmo.lab5.form;

import ru.itmo.lab5.form.field.Field;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Form {
    protected List<Field<?>> fields;
    protected Scanner scanner;
    protected PrintWriter printWriter;

    public Form(PrintWriter printWriter) {
        this.fields = new ArrayList<>();
        this.printWriter = printWriter;
    }

    public Form(List<Field<?>> fields, PrintWriter printWriter) {
        this.fields = fields;
        this.printWriter = printWriter;
    }

    void addField(Field<?> newField) {
        this.fields.add(newField);
    }

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
