package lib.lang;

import java.util.Locale;

public enum Language {
    ENGLISH("English", "en_US"),
    CZECH("Czech", "cs_CZ"),
    ENGLISH_NEW_ZEALAND("English (New Zealand)", "en_NZ"),
    RUSSIAN("Russian", "ru_RU"),
    ALBANIAN("Albanian", "sq_AL");

    private final String humanName;
    private final String code;
    private final Locale locale;

    Language(String humanName, String code) {
        this.humanName = humanName;
        this.code = code;
        this.locale = Locale.forLanguageTag(this.code.replace('_', '-'));
    }

    public String getHumanName() {
        return this.humanName;
    }

    public String getCode() {
        return this.code;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public static Language getByHumanName(String humanName) {
        for (var language : Language.values()) {
            if (language.getHumanName().equals(humanName)) {
                return language;
            }
        }
        return null;
    }
}

