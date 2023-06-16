package lib.form.field;

import lib.form.validation.ValidationException;
import lib.form.validation.Validator;

import java.io.PrintWriter;
import java.util.*;

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
    public void printInfoMessage(ResourceBundle messageBundle) {
        this.printWriter.print(messageBundle.getString("message.enter") + " `" + this.name + "` (" + messageBundle.getString("message.choices") + ": ");

        this.printWriter.print(getBeautifiedChoices());

        this.printWriter.print("): ");

        this.printWriter.flush();
    }

    @Override
    public void parseRawValue(ResourceBundle messageBundle) {
        this.value = Enum.valueOf(this.enumClass, this.rawValue);
    }

    @Override
    public void validateRawValue(ResourceBundle messageBundle) throws ValidationException {
        super.validateRawValue(messageBundle);
        if (this.rawValue == null) {
            throw new ValidationException(messageBundle.getString("error.field.cantBeNull"));
        }
        try {
            Enum.valueOf(this.enumClass, this.rawValue.trim());
        } catch(IllegalArgumentException e){
            throw new ValidationException(messageBundle.getString("message.pleaseEnter") + " `" + this.name + "` (" + messageBundle.getString("message.choices") + ": " + getBeautifiedChoices() + "): ");
        }
    }
}
