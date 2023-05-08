package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class IntegerField extends Field<Integer> {

    public IntegerField(String name, PrintWriter printWriter) {
        super(name, printWriter);
    }

    public IntegerField(String name, List<Validator<String>> rawValueValidators, List<Validator<Integer>> valueValidators, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, printWriter);
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
