package ru.itmo.lab5.schema;

public class Location implements Comparable<Location> {
    private Double x; //Поле не может быть null
    private int y;
    private String name; //Поле не может быть null

    public Double getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Location other) {
        int xComparisonResult = Double.compare(Math.abs(this.x), Math.abs(other.x));
        if (xComparisonResult != 0) {
            return xComparisonResult;
        }

        int yComparisonResult = Integer.compare(Math.abs(this.y), Math.abs(other.y));
        if (yComparisonResult != 0) {
            return yComparisonResult;
        }

        if (!this.name.equals(other.name)) {
            return this.name.compareTo(other.name);
        }

        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 23 + this.x.hashCode();
        hash = hash * 23 + this.y;
        hash = hash * 23 + this.name.hashCode();
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

        final Location other = (Location) obj;

        if (Double.compare(this.x, other.x) != 0) {
            return false;
        }

        if (this.y != other.y) {
            return false;
        }

        if (!this.name.equals(other.name)) {
            return false;
        }

        return true;
    }
}
