package ru.itmo.lab5.form.validation;

public interface Validator<T> {
    void validate(T value) throws ValidationException;
}
