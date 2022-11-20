package ru.itmo.lab3.mental;

public enum CryLevel {
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

    public CryLevel prev() {
        int prevIndex = Math.max(this.ordinal() - 1, 0);
        return allValues[prevIndex];
    }

    public CryLevel next() {
        int nextIndex = Math.min(this.ordinal() + 1, allValuesLength - 1);
        return allValues[nextIndex];
    }

    @Override
    public String toString() {
        return this.description;
    }
}
