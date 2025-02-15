package org.example.part3;

public class Place implements Nameable {
    private String name;
    private int width;
    private int height;

    public Place(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public String name() {
        return this.name + " (place)";
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int area() {
        return this.height * this.width;
    }
}
