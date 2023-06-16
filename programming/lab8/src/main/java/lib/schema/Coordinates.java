package lib.schema;

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
    static final long serialVersionUID = 1;
    private int id;
    private int ownerID;
    private Float x; //Значение поля должно быть больше -527, Поле не может быть null
    private int y; //Максимальное значение поля: 897

    public int getID() {
        return id;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public Float getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Coordinates other) {
        int xComparisonResult = Float.compare(Math.abs(this.x), Math.abs(other.x));
        if (xComparisonResult != 0) {
            return xComparisonResult;
        }

        int yComparisonResult = Integer.compare(Math.abs(this.y), Math.abs(other.y));
        if (yComparisonResult != 0) {
            return yComparisonResult;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 23 + Float.floatToIntBits(this.x);
        hash = hash * 23 + y;
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

        final Coordinates other = (Coordinates) obj;

        if (Float.compare(this.x, other.x) != 0) {
            return false;
        }

        if (this.y != other.y) {
            return false;
        }

        return true;
    }
}
