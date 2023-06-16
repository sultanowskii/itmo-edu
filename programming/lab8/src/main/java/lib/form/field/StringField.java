package lib.form.field;

import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class StringField extends Field<String> {

    public StringField(String name, PrintWriter printWriter) {
        super(name, printWriter);
    }

    public StringField(String name, List<Validator<String>> rawValueValidators, List<Validator<String>> valueValidators, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, printWriter);
    }

    public String getRawValue(String rawString) {
        return rawString;
    }

    @Override
    public void printInfoMessage(ResourceBundle messageBundle) {
        this.printWriter.print(messageBundle.getString("message.enter") + " `" + this.name + "` (" + messageBundle.getString("type.string") + "): ");
        this.printWriter.flush();
    }

    @Override
    public void parseRawValue(ResourceBundle messageBundle) {
        this.value = this.rawValue.trim();
    }
}
