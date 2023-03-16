package ru.itmo.lab5.form.field;

import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class DoubleField extends Field<Double> {

    public DoubleField(String name, Scanner scanner, PrintWriter printWriter) {
        super(name, scanner, printWriter);
    }

    public DoubleField(String name, List<Validator<String>> rawValueValidators, List<Validator<Double>> valueValidators, Scanner scanner, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, scanner, printWriter);
    }

    @Override
    public void printInfoMessage() {
        this.printWriter.print("Enter `" + this.name + "` (Floating point number): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue() {
        this.value = Double.parseDouble(this.rawValue);
    }

    @Override
    public void validateRawValue() throws ValidationException {
        super.validateRawValue();
        try {
            Double.parseDouble(this.rawValue.trim());
        } catch(NumberFormatException e){
            throw new ValidationException("Please enter floating point number.");
        }
    }
}
