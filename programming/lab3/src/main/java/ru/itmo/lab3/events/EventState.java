package ru.itmo.lab3.events;

public enum EventState {
    RUINED("Ruined"),
    SPOILED("Spoiled"),
    USUAL("Usual"),
    COOL("Cool"),
    AWESOME("Awesome");

    private String description;
    private static final EventState[] allValues = values();
    private static final int allValuesLength = values().length;

    private EventState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public EventState prev() {
        int prevIndex = Math.max(this.ordinal() - 1, 0);
        return allValues[prevIndex];
    }

    public EventState next() {
        int nextIndex = Math.min(this.ordinal() + 1, allValuesLength - 1);
        return allValues[nextIndex];
    }

    @Override
    public String toString() {
        return this.description;
    }
}
