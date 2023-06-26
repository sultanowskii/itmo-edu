package client.gui.util;

import client.runtime.ClientContext;
import lib.schema.Person;

public class PersonValidator {
    public static String getPersonErrors(ClientContext context, Person person) {
        var mb = context.getLocalizationManager().getMessageBundle();

        if (person.getName().isEmpty()) {
            return mb.getString("gui.label.name") +  ": " + mb.getString("error.cantBeBlank");
        }

        if (person.getCoordinatesX() <= -527) {
            return mb.getString("gui.label.coordinatesX") + ": " + mb.getString("error.validation.gt") + " 527";
        }

        if (person.getCoordinatesY() > 897) {
            return mb.getString("gui.label.coordinatesY") + ": " + mb.getString("error.validation.lte") + " 897";
        }

        if (person.getHeight() <= 0) {
            return mb.getString("gui.label.height") + ": " + mb.getString("error.validation.gt") + " 0";
        }

        if (person.getPassportID().isEmpty()) {
            return mb.getString("gui.label.passportID") +  ": " + mb.getString("error.cantBeBlank");
        }

        if (person.getPassportID().length() > 44) {
            return mb.getString("gui.label.passportID") +  ": " + mb.getString("error.validation.tooLong") + " [1; 43]";
        }

        if (person.getLocationName().isEmpty()) {
            return mb.getString("gui.label.locationName") +  ": " + mb.getString("error.cantBeBlank");
        }

        return null;
    }
}
