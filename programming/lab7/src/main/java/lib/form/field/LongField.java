package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class LongField extends Field<Long> {
    public LongField(String name, PrintWriter printWriter) {
        super(name, printWriter);
    }

    public LongField(String name, List<Validator<String>> rawValueValidators, List<Validator<Long>> valueValidators, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, printWriter);
    }

    @Override
    public void printInfoMessage() {
        this.printWriter.print("Enter `" + this.name + "` (Long integer nubmer): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue() {
        this.value = Long.parseLong(this.rawValue);
    }

    @Override
    public void validateRawValue() throws ValidationException {
        super.validateRawValue();
        try {
            Long.parseLong(this.rawValue.trim());
        } catch(NumberFormatException e){
            throw new ValidationException("Please enter long integer number.");
        }
    }
}
