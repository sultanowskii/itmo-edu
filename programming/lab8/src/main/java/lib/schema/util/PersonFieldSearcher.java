package lib.schema.util;

import lib.schema.Person;

import java.util.function.Predicate;

public class PersonFieldSearcher {
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
    public static Predicate<Person> fieldContainsValue(String field, String value) {
        if (field == null) {
            return person -> true;
        }

        return switch (field) {
            case "id" -> person -> person.getID().toString().contains(value);
            case "name" -> person -> person.getName().contains(value);
            case "coordinatesX" -> person -> Float.toString(person.getCoordinatesX()).contains(value);
            case "coordinatesY" -> person -> Integer.toString(person.getCoordinatesY()).contains(value);
            case "creationDate" -> person -> person.getCreationDate().toString().contains(value);
            case "height" -> person -> Long.toString(person.getHeight()).contains(value);
            case "passportID" -> person -> person.getPassportID().contains(value);
            case "eyeColor" -> person -> person.getEyeColor().name().contains(value);
            case "nationality" -> person -> person.getNationality().name().contains(value);
            case "locationName" -> person -> person.getLocationName().contains(value);
            case "locationX" -> person -> Double.toString(person.getLocationX()).contains(value);
            case "locationY" -> person -> Integer.toString(person.getLocationY()).contains(value);
            default -> person -> true;
        };

    }
}
