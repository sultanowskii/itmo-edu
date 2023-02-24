package ru.itmo.lab5.command;

import ru.itmo.lab5.form.Form;
import ru.itmo.lab5.form.field.*;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.schema.*;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class ShowCommand extends Command {

    public ShowCommand(Scanner scanner, PrintWriter printWriter) {
        super("show", scanner, printWriter);
    }

    @Override
    public void exec(List<String> args, Context context) {
        PersonManager personStorage = context.getPersonManager();
        HashSet<Person> persons = personStorage.getStorage();

        // TODO: выпилить
        for (int i = 0; i < 5; i++) {
            Coordinates coordinates = new Coordinates();
            coordinates.setX(1.0f);
            coordinates.setY(2);
            Location location = new Location();
            location.setName("city #" + i);
            location.setX(3d);
            location.setY(4);
            Person person = new Person();
            person.setCoordinates(coordinates);
            person.setCreationDate(ZonedDateTime.now());
            person.setNationality(Country.THAILAND);
            person.setName("ivan");
            person.setHeight(170);
            person.setEyeColor(Color.RED);
            person.setPassportID("aaaa");
            person.setLocation(location);
            persons.add(person);
        }

        List<Field<?>> coordinatesFields = new ArrayList<>();
        coordinatesFields.add(new FloatField("x", this.scanner, this.printWriter));
        coordinatesFields.add(new IntegerField("y", this.scanner, this.printWriter));

        List<Field<?>> locationFields = new ArrayList<>();
        locationFields.add(new DoubleField("x", this.scanner, this.printWriter));
        locationFields.add(new IntegerField("y", this.scanner, this.printWriter));
        locationFields.add(new StringField("name", this.scanner, this.printWriter));

        List<Field<?>> fields = new ArrayList<>();

        String pattern = "dd/MM/yyyy hh:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        fields.add(new IntegerField("id", this.scanner, this.printWriter));
        fields.add(new StringField("name", this.scanner, this.printWriter));
        fields.add(new ObjectField<Coordinates>("coordinates", coordinatesFields, this.scanner, this.printWriter));
        fields.add(new ZonedDateTimeField("creationDate", formatter, pattern, this.scanner, this.printWriter));
        fields.add(new LongField("height", this.scanner, this.printWriter));
        fields.add(new StringField("passportID", this.scanner, this.printWriter));
        fields.add(new EnumField<Color>("eyeColor", Color.class, this.scanner, this.printWriter));
        fields.add(new EnumField<Country>("nationality", Country.class, this.scanner, this.printWriter));
        fields.add(new ObjectField<Location>("location", locationFields, this.scanner, this.printWriter));

        Form personForm = new Form(fields, this.printWriter);

        for (Person person : persons) {
            printWriter.println(personForm.getStringifiedValueFromObject(person, 0));
        }
    }

    @Override
    public String getHelp() {
        return "Show all elements of collection. Syntax: " + this.getName();
    }
}
