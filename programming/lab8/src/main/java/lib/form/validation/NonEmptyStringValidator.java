package lib.form.validation;

import java.util.ResourceBundle;

public class NonEmptyStringValidator implements Validator<String> {
    @Override
    public void validate(String value, ResourceBundle messageBundle) throws ValidationException {
        NonNullValidator<String> nonNullValidator = new NonNullValidator<>();
        nonNullValidator.validate(value, messageBundle);
        if (value.length() == 0)
            throw new ValidationException(messageBundle.getString("error.validation.nonEmptyString"));
    }
}
