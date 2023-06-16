package lib.form.validation;

import java.util.ResourceBundle;

/**
 * Field value validator
 * @param <T> Field type
 */
public interface Validator<T> {
    /**
     * Validate value
     * @param value Value to be validated
     * @throws ValidationException If validation failed
     */
    void validate(T value, ResourceBundle messageBundle) throws ValidationException;
}
