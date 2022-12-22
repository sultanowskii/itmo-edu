package ru.itmo.lab4.events;

import java.util.ListIterator;

public class Event {
    public enum State implements ListIterator<State> {
        RUINED("Ruined"),
        SPOILED("Spoiled"),
        USUAL("Usual"),
        COOL("Cool"),
        AWESOME("Awesome");

        private String description;
        private static final State[] allValues = values();
        private static final int allValuesLength = values().length;

        State(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        public State previous() {
            int prevValueIndex = previousIndex();
            return allValues[prevValueIndex];
        }

        public State next() {
            int nextValueIndex = nextIndex();
            return allValues[nextValueIndex];
        }

        public boolean hasNext() {
            return this.ordinal() + 1 < allValuesLength;
        }

        public boolean hasPrevious() {
            return this.ordinal() == 0;
        }

        public int nextIndex() {
            return Math.min(this.ordinal() + 1, allValuesLength - 1);
        }

        public int previousIndex() {
            return Math.max(this.ordinal() - 1, 0);
        }

        public void remove() {}

        public void set(State eventState) {}

        public void add(State eventState) {}

        @Override
        public String toString() {
            return this.description;
        }
    }

    private String name;
    private State state;

    public Event(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public Event(String name) {
        this(name, Event.State.USUAL);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

    public Event.State getState() {
        return this.state;
    }

    public void worsen() {
        Event.State newState = this.state.previous();
        if (!newState.equals(this.state)) {
            this.state = newState;
            System.out.println(this + " is worsen. It is " + this.state + " now.");
        }
    }

    public void improve(String wayToImprove) {
        Event.State newState = this.state.next();
        if (!newState.equals(this.state)) {
            this.state = newState;
            System.out.println(this + " is improved (" + wayToImprove + "). It is " + this.state + " now.");
        }
    }

    @Override
    public int hashCode() {
        return ("Event_" + this.getName()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        boolean haveSameClass = this.getClass() == obj.getClass();
        if (!haveSameClass)
            return false;

        Event otherEvent = (Event) obj;

        return this.hashCode() == otherEvent.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}

