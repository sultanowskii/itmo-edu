package ru.itmo.lab5.schema;

import java.time.ZonedDateTime;

public class Person implements Comparable<Person> {
    private static int nextId = 1;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long height; //Значение поля должно быть больше 0
    private String passportID; //Длина строки не должна быть больше 46, Строка не может быть пустой, Значение этого поля должно быть уникальным, Поле не может быть null
    private Color eyeColor; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null

    public Person() {
        this.creationDate = ZonedDateTime.now();
        this.id = getIncNextId();
        this.coordinates = new Coordinates();
        this.location = new Location();
    }

    public int getNextId() {
        return nextId;
    }

    // TODO: Придумать название получше
    public int getIncNextId() {
        return nextId++;
    }

    // Для случаев, когда загрузили данные из файла и нужно выставить релевантный ID
    public static void setNextId(int newNextId) {
        nextId = newNextId;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return this.creationDate;
    }

    public long getHeight() {
        return this.height;
    }

    public String getPassportID() {
        return this.passportID;
    }

    public Color getEyeColor() {
        return this.eyeColor;
    }

    public Country getNationality() {
        return this.nationality;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int compareTo(Person other) {
        if (this.height != other.height) {
            return Long.compare(this.height, other.height);
        }

        if (!this.creationDate.equals(other.creationDate)) {
            return this.creationDate.compareTo(other.creationDate);
        }

        if (!this.name.equals(other.name)) {
            return this.name.compareTo(other.name);
        }

        if (!this.passportID.equals(other.passportID)) {
            return this.passportID.compareTo(other.passportID);
        }

        if (!this.coordinates.equals(other.coordinates)) {
            return this.coordinates.compareTo(other.coordinates);
        }

        if (!this.id.equals(other.id)) {
            return this.id.compareTo(other.id);
        }

        if (!this.eyeColor.equals(other.eyeColor)) {
            return this.eyeColor.compareTo(other.eyeColor);
        }

        if (!this.nationality.equals(other.nationality)) {
            return this.nationality.compareTo(other.nationality);
        }

        if (!this.location.equals(other.location)) {
            return this.location.compareTo(other.location);
        }

        return 0;
    }
}
