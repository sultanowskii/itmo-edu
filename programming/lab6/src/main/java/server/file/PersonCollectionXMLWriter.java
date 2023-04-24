package server.file;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import lib.date.DateTimeFormatterBuilder;
import server.file.util.JdomXMLFormat;
import server.manager.PersonManager;
import lib.schema.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PersonCollectionXMLWriter implements CollectionManagerWriter<PersonManager> {
    protected Scanner scanner;
    protected PrintWriter printWriter;
    protected OutputStream xmlOutputStream;

    public PersonCollectionXMLWriter(Scanner scanner, PrintWriter printWriter, OutputStream xmlOutputStream) {
        this.scanner = scanner;
        this.printWriter = printWriter;
        this.xmlOutputStream = xmlOutputStream;
    }

    @Override
    public void writeCollection(PersonManager personManager) throws IOException {
        org.jdom2.Document document = new Document();

        Element rootElement = new Element("collection");
        document.setRootElement(rootElement);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatterBuilder.getDateTimeFormatter();

        ZonedDateTime collectionInitDate = personManager.getInitDateTime();
        if (collectionInitDate != null) {
            rootElement.addContent(new Element("initDateTime").setText(collectionInitDate.format(dateTimeFormatter)));
        }

        Element personStorageElement = new Element("storage");

        for (Person person : personManager.getStorage()) {
            Element personElement = new Element("person");

            personElement.addContent(new Element("id").setText(person.getID().toString()));
            personElement.addContent(new Element("name").setText(person.getName()));

            {
                Element coordinatesElement = new Element("coordinates");
                Coordinates coordinates = person.getCoordinates();
                if (coordinates != null) {
                    coordinatesElement.addContent(new Element("x").setText(coordinates.getX().toString()));
                    coordinatesElement.addContent(new Element("y").setText(Integer.toString(coordinates.getY())));

                    personElement.addContent(coordinatesElement);
                }
            }

            ZonedDateTime creationDate = person.getCreationDate();
            if (creationDate != null) {
                personElement.addContent(new Element("creationDate").setText(creationDate.format(dateTimeFormatter)));
            }
            personElement.addContent(new Element("height").setText(Long.toString(person.getHeight())));
            personElement.addContent(new Element("passportID").setText(person.getPassportID()));
            personElement.addContent(new Element("eyeColor").setText(person.getEyeColor().toString()));
            personElement.addContent(new Element("nationality").setText(person.getNationality().toString()));

            {
                Element locationElement = new Element("location");
                Location location = person.getLocation();
                if (location != null) {
                    locationElement.addContent(new Element("x").setText(Double.toString(location.getX())));
                    locationElement.addContent(new Element("y").setText(Integer.toString(location.getY())));
                    locationElement.addContent(new Element("name").setText(location.getName()));

                    personElement.addContent(locationElement);
                }
            }

            personStorageElement.addContent(personElement);
        }
        rootElement.addContent(personStorageElement);

        XMLOutputter xmlOutputter = new XMLOutputter();

        xmlOutputter.setFormat(JdomXMLFormat.getPrettyXMLJdomFormatWith4SpacesIndent());
        xmlOutputter.output(document, this.xmlOutputStream);
    }
}
