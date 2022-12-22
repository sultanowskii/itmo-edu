package ru.itmo.lab4;

public abstract class Entity {
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
