package client.gui.component;

import javax.swing.*;
import javax.swing.text.*;

public class IntegerField extends JTextField {
    public IntegerField() {
        super();
        setDocument(new IntegerDocument());
    }

    public IntegerField(int columns) {
        super(columns);
        setDocument(new IntegerDocument());
    }

    private static class IntegerDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            if (str.matches("-?[0-9]*")) {
                super.insertString(offs, str, a);
            }
        }
    }
}
