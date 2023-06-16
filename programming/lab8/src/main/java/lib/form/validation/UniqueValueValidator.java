package lib.form.validation;

import java.util.ResourceBundle;
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
    public void validate(T value, ResourceBundle messageBundle) throws ValidationException {
        if (occupiedValues.contains(value))
            throw new ValidationException(messageBundle.getString("error.validation.uniquenessViolation"));
    }
}
