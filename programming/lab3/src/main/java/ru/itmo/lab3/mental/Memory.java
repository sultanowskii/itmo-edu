package ru.itmo.lab3.mental;

public class Memory {
    private final String description;

    public Memory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public int hashCode() {
        return ("Memory_" + this.getDescription()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        boolean haveSameClass = this.getClass() == obj.getClass();
        if (!haveSameClass)
            return false;

        Memory otherLocation = (Memory) obj;

        return this.hashCode() == otherLocation.hashCode();
    }
}
