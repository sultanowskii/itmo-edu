package server.file.util;

import org.jdom2.Element;

/**
 * Getter of Jdom child element value with default value.
 */
public class JdomChildValueGetterWithDefaultValue {
    String defaultValue = "";

    /**
     * Default constructor
     */
    public JdomChildValueGetterWithDefaultValue() {

    }

    /**
     * Constructor with default value
     * @param defaultValue Default value to be used if child element is not found
     */
    public JdomChildValueGetterWithDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Get child element's value or default one if child doesn't exist
     * @param element Element to be inspected
     * @param childName Name of the child element
     * @return Child's value or default value
     */
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
