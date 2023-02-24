package ru.itmo.lab5.schema;

public class Coordinates implements Comparable<Coordinates> {
    private Float x; //Значение поля должно быть больше -527, Поле не может быть null
    private int y; //Максимальное значение поля: 897

    public Float getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
}
