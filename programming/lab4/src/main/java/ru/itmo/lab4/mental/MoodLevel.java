package ru.itmo.lab4.mental;

import java.util.ListIterator;

public enum MoodLevel implements ListIterator<MoodLevel> {
    DEPRESSED("Depressed"),
    DISAPPOINTED("Disappointed"),
    UPSET("Upset"),
    SAD("Sad"),
    NEUTRAL("Sad"),
    OK("OK"),
    COOL("Cool"),
    POSITIVE("Positive"),
    FUN("Fun"),
    HAPPY("Happy");

    private String description;
    private static final MoodLevel[] allValues = values();
    private static final int allValuesLength = values().length;

    private MoodLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int previousIndex() {
        return Math.max(this.ordinal() - 1, 0);
    }

    public MoodLevel previous() {
        int previousValueIndex = previousIndex();
        return allValues[previousValueIndex];
    }

    public int nextIndex() {
        return Math.min(this.ordinal() + 1, allValuesLength - 1);
    }

    public MoodLevel next() {
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

    public void set(MoodLevel cryLevel) {}

    public void add(MoodLevel cryLevel) {}

    @Override
    public String toString() {
        return this.description;
    }
}
