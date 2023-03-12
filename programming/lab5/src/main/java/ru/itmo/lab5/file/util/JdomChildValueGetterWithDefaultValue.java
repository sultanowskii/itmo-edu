package ru.itmo.lab5.file.util;

import org.jdom2.Element;

public class JdomChildValueGetterWithDefaultValue {
    String defaultValue = "";

    public JdomChildValueGetterWithDefaultValue() {

    }

    public JdomChildValueGetterWithDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getChildValueOrDefault(Element element, String childName) {
        if (element == null) {
            return this.defaultValue;
        }

        Element childElement = element.getChild(childName);

        if (childElement == null) {
            return this.defaultValue;
        }

        return childElement.getValue();
    }
}
