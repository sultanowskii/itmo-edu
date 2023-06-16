package lib.form.validation;

import java.util.ResourceBundle;

public class StringLengthValidator implements Validator<String> {
    protected int minLength;
    protected int maxLength;

    public StringLengthValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public void validate(String value, ResourceBundle messageBundle) throws ValidationException {
        if (value.length() < minLength)
            throw new ValidationException(messageBundle.getString("error.validation.tooShort") + " >= " + minLength + ".");
        if (value.length() > maxLength)
            throw new ValidationException(messageBundle.getString("error.validation.tooLong") + " <= " + maxLength + ".");
    }
}
