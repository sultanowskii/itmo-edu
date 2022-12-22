package ru.itmo.lab4.mental;

import java.util.ListIterator;

public enum CryLevel implements ListIterator<CryLevel> {
    NOT_CRYING("Not crying"),
    SNIFFLING("Sniffing"),
    MODERATE("Moderate"),
    TREMBLING("Trembling"),
    MOANING("Moaning"),
    HYSTERICAL("Hysterical");

    private String description;
    private static final CryLevel[] allValues = values();
    private static final int allValuesLength = values().length;

    private CryLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int previousIndex() {
        return Math.max(this.ordinal() - 1, 0);
    }

    public CryLevel previous() {
        int previousValueIndex = previousIndex();
        return allValues[previousValueIndex];
    }

    public int nextIndex() {
        return Math.min(this.ordinal() + 1, allValuesLength - 1);
    }

    public CryLevel next() {
        int nextValueIndex = nextIndex();
        return allValues[nextValueIndex];
    }

    public boolean hasNext() {
        return this.ordinal() + 1 < allValuesLength;
    }

    public boolean hasPrevious() {
        return this.ordinal() == 0;
    }

    public void remove() {}

    public void set(CryLevel cryLevel) {}

    public void add(CryLevel cryLevel) {}

    @Override
    public String toString() {
        return this.description;
    }
}
