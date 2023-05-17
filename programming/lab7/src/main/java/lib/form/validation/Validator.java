package lib.form.validation;

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
    void validate(T value) throws ValidationException;
}
