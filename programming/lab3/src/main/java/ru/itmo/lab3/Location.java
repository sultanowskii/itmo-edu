package ru.itmo.lab3;

import java.util.List;
import java.util.ArrayList;

public class Location extends Entity {
    private final List<Location> containedLocations;

    public Location(String name) {
        super(name);
        this.containedLocations = new ArrayList<>();
    }

    public void addInnerLocation(Location otherLocation) {
        this.containedLocations.add(otherLocation);
    }

    @Override
    public int hashCode() {
        return ("Location_" + this.getName()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        boolean haveSameClass = this.getClass() == obj.getClass();
        if (!haveSameClass)
            return false;

        Location otherLocation = (Location) obj;

        if (this.hashCode() != otherLocation.hashCode())
            return false;

        return this.containedLocations.equals(otherLocation.containedLocations);
    }
}
