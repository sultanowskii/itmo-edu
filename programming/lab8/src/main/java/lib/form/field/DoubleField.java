package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DoubleField extends Field<Double> {

    public DoubleField(String name, PrintWriter printWriter) {
        super(name, printWriter);
    }

    public DoubleField(String name, List<Validator<String>> rawValueValidators, List<Validator<Double>> valueValidators, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, printWriter);
    }

    @Override
    public void printInfoMessage(ResourceBundle messageBundle) {
        this.printWriter.print(messageBundle.getString("message.enter") + " `" + this.name + "` (" + messageBundle.getString("type.double") + "): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue(ResourceBundle messageBundle) {
        this.value = Double.parseDouble(this.rawValue);
    }

    @Override
    public void validateRawValue(ResourceBundle messageBundle) throws ValidationException {
        super.validateRawValue(messageBundle);
        try {
            Double.parseDouble(this.rawValue.trim());
        } catch(NumberFormatException e){
            throw new ValidationException(messageBundle.getString("message.pleaseEnter") + " " + messageBundle.getString("type.double") + ": ");
        }
    }
}
