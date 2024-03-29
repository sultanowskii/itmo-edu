package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class EnumField<E extends Enum<E>> extends Field<E> {
    protected Class<E> enumClass;

    public EnumField(String name, Class<E> enumClass, PrintWriter printWriter) {
        super(name, printWriter);
        this.enumClass = enumClass;
    }

    public EnumField(String name, Class<E> enumClass, List<Validator<String>> rawValueValidators, List<Validator<E>> valueValidators, PrintWriter printWriter) {
        super(name, rawValueValidators, valueValidators, printWriter);
        this.enumClass = enumClass;
    }

    public String getBeautifiedChoices() {
        StringJoiner joiner = new StringJoiner(", ");
        List<?> choiceList = Arrays.asList(enumClass.getEnumConstants());
        choiceList.forEach(item -> joiner.add(item.toString()));
        return joiner.toString();
    }

    @Override
    public void printInfoMessage() {
        this.printWriter.print("Enter `" + this.name + "` (choices: ");

        this.printWriter.print(getBeautifiedChoices());

        this.printWriter.print("): ");

        this.printWriter.flush();
    }

    @Override
    public void parseRawValue() {
        this.value = Enum.valueOf(this.enumClass, this.rawValue);
    }

    @Override
    public void validateRawValue() throws ValidationException {
        super.validateRawValue();
        if (this.rawValue == null) {
            throw new ValidationException("This field cannot be null.");
        }
        try {
            Enum.valueOf(this.enumClass, this.rawValue.trim());
        } catch(IllegalArgumentException e){
            throw new ValidationException("Please enter valid value (choices: " + getBeautifiedChoices() + "): ");
        }
    }
}
