package web.lab3.server.validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;


/**
 * Валидатор значения координаты.
 */
@FacesValidator("coordValidator")
public class CoordValidator implements Validator<Object> {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        validateNotNull(value);
        Double coord = validateIsDouble(value);
    }

    /**
     * Проверить на null.
     *
     * @param value значение
     * @throws ValidatorException если валидация не прошла
     */
    private void validateNotNull(Object value) throws ValidatorException {
        if (value != null) {
            return;
        }

        FacesMessage message = new FacesMessage("Значение не должно быть пустым");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    /**
     * Проверить, соответствует ли типу Double.
     *
     * @param value значение
     * @return значение, сконвертированное в Double
     * @throws ValidatorException если валидация не прошла
     */
    private Double validateIsDouble(Object value) throws ValidatorException {
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            FacesMessage message = new FacesMessage("Значение должно быть вещественным числом");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }
}
