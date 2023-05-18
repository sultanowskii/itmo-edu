package lib.form;

import lib.form.field.Field;
import lib.form.field.StringField;
import lib.form.validation.NonEmptyStringValidator;
import lib.form.validation.NonNullValidator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CredentialsFormCreator {
    private static List<Field<?>> getFields(PrintWriter printWriter) {
        List<Field<?>> fields = new ArrayList<>();

        fields.add(new StringField("login", printWriter).addValueValidator(new NonEmptyStringValidator()));
        fields.add(new StringField("password", printWriter).addValueValidator(new NonEmptyStringValidator()));

        for (Field<?> field : fields) {
            field.addRawValueValidator(new NonNullValidator<>());
        }

        return fields;
    }

    public static Form getForm(PrintWriter printWriter) {
        List<Field<?>> fields = getFields(printWriter);
        return new Form(fields, printWriter);
    }
}
