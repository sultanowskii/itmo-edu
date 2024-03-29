package ru.itmo.lab5.file;

import org.jdom2.Element;
import org.xml.sax.SAXException;
import ru.itmo.lab5.date.DateTimeFormatterBuilder;
import ru.itmo.lab5.file.util.JdomChildValueGetterWithDefaultValue;
import ru.itmo.lab5.file.util.JdomDocumentCreator;
import ru.itmo.lab5.form.PersonCreationFormCreator;
import ru.itmo.lab5.form.PersonRetrieveFormCreator;
import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Person collection XML reader
 */
public class PersonCollectionXMLReader implements CollectionManagerReader<PersonManager> {
    protected Scanner scanner;
    protected PrintWriter printWriter;
    protected Scanner xmlScanner;

    /**
     * Deafult constructor
     * @param scanner Global input (to get user input)
     * @param printWriter Global output (to print something to user)
     * @param xmlScanner XML scanner to read data from
     */
    public PersonCollectionXMLReader(Scanner scanner, PrintWriter printWriter, Scanner xmlScanner) {
        this.scanner = scanner;
        this.printWriter = printWriter;
        this.xmlScanner = xmlScanner;
    }

    @Override
    public PersonManager readCollectionManager() throws NoSuchFieldException, IllegalAccessException {
        org.jdom2.Document document;
        try {
            document = JdomDocumentCreator.createJDOMDocumentwithDOMParser(this.xmlScanner);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error occurred during collection file parse. Details: " + e.getMessage());
        } catch (SAXException e) {
            throw new RuntimeException("Collection file seems to be invalid.");
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Programming error during collection file parse. Details: " + e.getMessage());
        }

        PersonManager personManager = new PersonManager(new LinkedHashSet<>());

        Element root = document.getRootElement();

        ZonedDateTimeField initDateTimeField = new ZonedDateTimeField(
      "initDateTime",
            DateTimeFormatterBuilder.getDateTimeFormatter(),
            DateTimeFormatterBuilder.getDateTimePattern(),
            this.scanner,
            this.printWriter
        );
        personManager.setInitDateTime(initDateTimeField.parseAndGetValue(root.getChild("initDateTime").getValue()));

        int maxID = -1;

        List<Element> personElements = root.getChild("storage").getChildren("person");

        for (Element personElement : personElements) {
            Person person = new Person();
            JdomChildValueGetterWithDefaultValue childValueGetter = new JdomChildValueGetterWithDefaultValue();
            person.setCoordinates(new Coordinates());
            person.setLocation(new Location());

            IntegerField idField = PersonRetrieveFormCreator.getIDField(this.scanner, this.printWriter);
            idField.parseAndSetValue(person, childValueGetter.getChildValueOrDefault(personElement, "id"));

            Field<String> nameField = PersonCreationFormCreator.getNameField(this.scanner, this.printWriter);
            nameField.parseAndSetValue(person, childValueGetter.getChildValueOrDefault(personElement, "name"));

            {
                Element coordinatesElement = personElement.getChild("coordinates");
                Field<Float> coordinatesXField = PersonCreationFormCreator.getCoordinatesXField(this.scanner, this.printWriter);
                coordinatesXField.parseAndSetValue(person.getCoordinates(), childValueGetter.getChildValueOrDefault(coordinatesElement, "x"));

                Field<Integer> coordinatesYField = PersonCreationFormCreator.getCoordinatesYField(this.scanner, this.printWriter);
                coordinatesYField.parseAndSetValue(person.getCoordinates(), childValueGetter.getChildValueOrDefault(coordinatesElement, "y"));
            }

            Field<ZonedDateTime> creationDateField = PersonRetrieveFormCreator.getCreationDateField(this.scanner, this.printWriter);
            creationDateField.parseAndSetValue(person, childValueGetter.getChildValueOrDefault(personElement, "creationDate"));

            Field<Long> heightField = PersonCreationFormCreator.getHeightField(this.scanner, this.printWriter);
            heightField.parseAndSetValue(person, childValueGetter.getChildValueOrDefault(personElement, "height"));

            Field<String> passportIDField = PersonCreationFormCreator.getPassportIDField(this.scanner, this.printWriter);
            passportIDField.parseAndSetValue(person, childValueGetter.getChildValueOrDefault(personElement, "passportID"));

            Field<Color> eyeColor = PersonCreationFormCreator.getEyeColorField(this.scanner, this.printWriter);
            eyeColor.parseAndSetValue(person, childValueGetter.getChildValueOrDefault(personElement, "eyeColor"));

            Field<Country> nationalityColor = PersonCreationFormCreator.getNationalityField(this.scanner, this.printWriter);
            nationalityColor.parseAndSetValue(person, childValueGetter.getChildValueOrDefault(personElement, "nationality"));

            {
                Element locationElement = personElement.getChild("location");
                Field<Double> locationXField = PersonCreationFormCreator.getLocationXField(this.scanner, this.printWriter);
                locationXField.parseAndSetValue(person.getLocation(), childValueGetter.getChildValueOrDefault(locationElement, "x"));

                Field<Integer> locationYField = PersonCreationFormCreator.getLocationYField(this.scanner, this.printWriter);
                locationYField.parseAndSetValue(person.getLocation(), childValueGetter.getChildValueOrDefault(locationElement, "y"));

                Field<String> locationNameField = PersonCreationFormCreator.getLocationNameField(this.scanner, this.printWriter);
                locationNameField.parseAndSetValue(person.getLocation(), childValueGetter.getChildValueOrDefault(locationElement, "name"));
            }

            personManager.addWithoutAutoID(person);
            maxID = Math.max(maxID, person.getID());
        }

        personManager.setNextID(maxID + 1);

        return personManager;
    }
}
