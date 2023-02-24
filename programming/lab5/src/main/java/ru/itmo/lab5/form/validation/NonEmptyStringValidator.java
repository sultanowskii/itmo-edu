package ru.itmo.lab5.form.validation;

public class NonEmptyStringValidator implements Validator<String> {
    @Override
    public void validate(String value) throws ValidationException {
        NonNullValidator<String> nonNullValidator = new NonNullValidator<>();
        nonNullValidator.validate(value);
        if (value.length() == 0)
            throw new ValidationException("This field cannot be empty (empty string).");
    }
}
