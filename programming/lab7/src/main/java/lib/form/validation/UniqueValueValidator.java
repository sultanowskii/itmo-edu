package lib.form.validation;

import java.util.Set;

public class UniqueValueValidator<T> implements Validator<T> {
    protected Set<T> occupiedValues;

    public UniqueValueValidator(Set<T> values) {
        this.occupiedValues = values;
    }

    public void addOccupiedValue(T occupiedValue) {
        this.occupiedValues.add(occupiedValue);
    }

    public void setOccupiedValues(Set<T> occupiedValues) {
        this.occupiedValues = occupiedValues;
    }

    @Override
    public void validate(T value) throws ValidationException {
        if (occupiedValues.contains(value))
            throw new ValidationException("Value `" + value + "` violates uniqueness of this field.");
    }
}
