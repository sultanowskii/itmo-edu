package ru.itmo.lab4.mental;

public class Thought {
    private final String description;

    public Thought(String description) {
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
        return ("Thought_" + this.getDescription()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        boolean haveSameClass = this.getClass() == obj.getClass();
        if (!haveSameClass)
            return false;

        Thought otherLocation = (Thought) obj;

        return this.hashCode() == otherLocation.hashCode();
    }
}
