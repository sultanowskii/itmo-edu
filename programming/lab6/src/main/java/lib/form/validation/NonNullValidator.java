package lib.form.validation;

public class NonNullValidator<T> implements Validator<T> {
    @Override
    public void validate(T value) throws ValidationException {
        if (value == null)
            throw new ValidationException("This field cannot be blank (null).");
    }
}
