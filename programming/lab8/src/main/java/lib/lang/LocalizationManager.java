package lib.lang;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    private ResourceBundle messageBundle;
    private Locale currentLocale;

    public LocalizationManager() {
        this.currentLocale = Locale.US;
        this.messageBundle = ResourceBundle.getBundle("MessageBundle", currentLocale);
    }

    public ResourceBundle getMessageBundle() {
        return messageBundle;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public boolean changeLanguage(Language language) {
        if (language == null || language.getLocale().equals(this.currentLocale)) {
            return false;
        }

        this.currentLocale = language.getLocale();
        this.messageBundle = ResourceBundle.getBundle("MessageBundle", this.currentLocale);

        return true;
    }
}
