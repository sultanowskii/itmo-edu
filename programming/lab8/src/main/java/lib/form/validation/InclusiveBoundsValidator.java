package lib.form.validation;

import java.util.ResourceBundle;

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
    public void validate(T value, ResourceBundle messageBundle) throws ValidationException {
        if (this.lowerBound != null && value.compareTo(this.lowerBound) < 0)
            throw new ValidationException(messageBundle.getString("error.validation.gte") + " " + lowerBound + ".");
        if (this.upperBound != null && value.compareTo(this.upperBound) > 0)
            throw new ValidationException(messageBundle.getString("error.validation.lte") + " " + lowerBound + ".");
    }
}
