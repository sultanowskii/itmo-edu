package ru.itmo.lab5.form.validation;

public class ExclusiveBoundsValidator<T extends Comparable<T>> implements Validator<T> {
    protected T lowerBound = null;
    protected T upperBound = null;

    public ExclusiveBoundsValidator() {
    }

    public ExclusiveBoundsValidator(T lowerBound, T upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static <T extends Comparable<T>> ExclusiveBoundsValidator<T> newExclusiveLowerBoundValidator(T lowerBound) {
        return new ExclusiveBoundsValidator<>(lowerBound, null);
    }

    public static <T extends Comparable<T>> ExclusiveBoundsValidator<T> newExclusiveUpperBoundValidator(T upperBound) {
        return new ExclusiveBoundsValidator<>(null, upperBound);
    }

    @Override
    public void validate(T value) throws ValidationException {
        if (this.lowerBound != null && value.compareTo(this.lowerBound) <= 0)
            throw new ValidationException("Value has to be greater than " + lowerBound + ".");
        if (this.upperBound != null && value.compareTo(this.upperBound) >= 0)
            throw new ValidationException("Value has to be less than " + upperBound + ".");
    }
}
