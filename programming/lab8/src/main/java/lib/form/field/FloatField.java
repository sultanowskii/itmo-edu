package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FloatField extends Field<Float> {

    public FloatField(String name, PrintWriter printWriter) {
        super(name, printWriter);
    }

    public FloatField(String name, List<Validator<String>> rawValueValidators, List<Validator<Float>> valueValidators, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, printWriter);
    }

    @Override
    public void printInfoMessage(ResourceBundle messageBundle) {
        this.printWriter.print(messageBundle.getString("message.enter") + " `" + this.name + "` (" + messageBundle.getString("type.float") + "): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue(ResourceBundle messageBundle) {
        this.value = Float.parseFloat(this.rawValue);
    }

    @Override
    public void validateRawValue(ResourceBundle messageBundle) throws ValidationException {
        super.validateRawValue(messageBundle);
        try {
            Float.parseFloat(this.rawValue.trim());
        } catch(NumberFormatException e){
            throw new ValidationException(messageBundle.getString("message.pleaseEnter") + " " + messageBundle.getString("type.float") + ": ");
        }
    }
}
