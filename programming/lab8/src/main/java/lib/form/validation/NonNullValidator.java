package lib.form.validation;

import java.util.ResourceBundle;

public class NonNullValidator<T> implements Validator<T> {
    @Override
    public void validate(T value, ResourceBundle messageBundle) throws ValidationException {
        if (value == null)
            throw new ValidationException(messageBundle.getString("error.validation.nonNull"));
    }
}
