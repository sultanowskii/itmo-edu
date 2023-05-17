package lib.form.validation;

public class InclusiveBoundsValidator<T extends Comparable<T>> implements Validator<T> {
    protected T lowerBound = null;
    protected T upperBound = null;

    public InclusiveBoundsValidator(T lowerBound, T upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static <T extends Comparable<T>> InclusiveBoundsValidator<T> newInclusiveLowerBoundValidator(T lowerBound) {
        return new InclusiveBoundsValidator<>(lowerBound, null);
    }

    public static <T extends Comparable<T>> InclusiveBoundsValidator<T> newInclusiveUpperBoundValidator(T upperBound) {
        return new InclusiveBoundsValidator<>(null, upperBound);
    }


    @Override
    public void validate(T value) throws ValidationException {
        if (this.lowerBound != null && value.compareTo(this.lowerBound) < 0)
            throw new ValidationException("Value has to be greater than or equal to " + lowerBound + ".");
        if (this.upperBound != null && value.compareTo(this.upperBound) > 0)
            throw new ValidationException("Value has to be less than or equal to " + upperBound + ".");
    }
}
