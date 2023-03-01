package ru.itmo.lab5.form.field;

import ru.itmo.lab5.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StringField extends Field<String> {

    public StringField(String name, Scanner scanner, PrintWriter printWriter) {
        super(name, scanner, printWriter);
    }

    public StringField(String name, List<Validator<String>> rawValueValidators, List<Validator<String>> valueValidators, Scanner scanner, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, scanner, printWriter);
    }

    public String getRawValue(String rawString) {
        return rawString;
    }

    @Override
    public void printInfoMessage() {
        this.printWriter.print("Enter `" + this.name + "` (String): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue() {
        this.value = this.rawValue;
    }
}
