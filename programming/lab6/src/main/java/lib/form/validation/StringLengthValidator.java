package lib.form.validation;

public class StringLengthValidator implements Validator<String> {
    protected int minLength;
    protected int maxLength;

    public StringLengthValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public void validate(String value) throws ValidationException {
        if (value.length() < minLength)
            throw new ValidationException("The value is too short. Lower limit: " + minLength + ".");
        if (value.length() > maxLength)
            throw new ValidationException("The value is too long. Limit: " + maxLength + ".");
    }
}
