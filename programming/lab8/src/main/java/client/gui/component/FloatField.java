package client.gui.component;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;

public class FloatField extends JFormattedTextField {
    public FloatField() {
        super(createFormatter());
    }

    private static NumberFormatter createFormatter() {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Float.class);
        formatter.setAllowsInvalid(true);
        return formatter;
    }
}
