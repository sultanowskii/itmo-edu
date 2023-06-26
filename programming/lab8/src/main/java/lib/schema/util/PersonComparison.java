package lib.schema.util;

import lib.schema.Person;

import java.util.Comparator;

public class PersonComparison {
    public static String[] FIELDS = {
            "",
            "id",
            "name",
            "coordinatesX",
            "coordinatesY",
            "creationDate",
            "height",
            "passportID",
            "eyeColor",
            "nationality",
            "locationName",
            "locationX",
            "locationY"
    };

    public static Comparator<Person> getFieldComparator(String field) {
        if (field == null) {
            return Comparator.naturalOrder();
        }

        return switch (field) {
            case "id" -> Comparator.comparing(Person::getID);
            case "name" -> Comparator.comparing(Person::getName);
            case "coordinatesX" -> Comparator.comparing(Person::getCoordinatesX);
            case "coordinatesY" -> Comparator.comparing(Person::getCoordinatesY);
            case "creationDate" -> Comparator.comparing(Person::getCreationDate);
            case "height" -> Comparator.comparing(Person::getHeight);
            case "passportID" -> Comparator.comparing(Person::getPassportID);
            case "eyeColor" -> Comparator.comparing(Person::getEyeColor);
            case "nationality" -> Comparator.comparing(Person::getNationality);
            case "locationName" -> Comparator.comparing(Person::getLocationName);
            case "locationX" -> Comparator.comparing(Person::getLocationX);
            case "locationY" -> Comparator.comparing(Person::getLocationY);
            default -> Comparator.naturalOrder();
        };
    }
}
