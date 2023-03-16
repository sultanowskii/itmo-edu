package ru.itmo.lab5.form.field;

import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class IntegerField extends Field<Integer> {

    public IntegerField(String name, Scanner scanner, PrintWriter printWriter) {
        super(name, scanner, printWriter);
    }

    public IntegerField(String name, List<Validator<String>> rawValueValidators, List<Validator<Integer>> valueValidators, Scanner scanner, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, scanner, printWriter);
    }

    @Override
    public void printInfoMessage() {
        this.printWriter.print("Enter `" + this.name + "` (Integer number): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue() {
        this.value = Integer.parseInt(this.rawValue);
    }

    @Override
    public void validateRawValue() throws ValidationException {
        super.validateRawValue();
        try {
            Integer.parseInt(this.rawValue.trim());
        } catch(NumberFormatException e){
            throw new ValidationException("Please enter integer number.");
        }
    }
}
