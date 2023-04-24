package lib.schema;

import java.time.ZonedDateTime;

public class Person implements Comparable<Person> {
    private static int nextID = 1;
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
        this.coordinates = new Coordinates();
        this.location = new Location();
    }

    public Integer getID() {
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

    public void setID(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 23 + this.coordinates.hashCode();
        hash = hash * 23 + this.creationDate.hashCode();
        hash = hash * 23 + this.eyeColor.hashCode();
        hash = hash * 23 + (int)(this.height >>> 32);
        hash = hash * 23 + this.id;
        hash = hash * 23 + this.location.hashCode();
        hash = hash * 23 + this.name.hashCode();
        hash = hash * 23 + this.nationality.hashCode();
        hash = hash * 23 + this.passportID.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Person other = (Person) obj;

        if ((int) this.id != other.id) {
            return false;
        }

        if (!this.passportID.equals(other.passportID)) {
            return false;
        }

        if (!this.name.equals(other.name)) {
            return false;
        }

        if (!this.creationDate.equals(other.creationDate)) {
            return false;
        }

        if (!this.eyeColor.equals(other.eyeColor)) {
            return false;
        }

        if (!this.nationality.equals(other.nationality)) {
            return false;
        }

        if (this.height != other.height) {
            return false;
        }

        if (!this.location.equals(other.location)) {
            return false;
        }

        if (!this.coordinates.equals(other.coordinates)) {
            return false;
        }

        return true;
    }
}
