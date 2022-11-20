package ru.itmo.lab3.events;

public class Event {
    private String name;
    private EventState state;

    public Event(String name, EventState state) {
        this.name = name;
        this.state = state;
    }

    public Event(String name) {
        this(name, EventState.USUAL);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

    public EventState getState() {
        return this.state;
    }

    public void worsen() {
        EventState newState = this.state.prev();
        if (!newState.equals(this.state)) {
            this.state = newState;
            System.out.println(this + " is worsen. It is " + this.state + " now.");
        }
    }

    public void improve() {
        EventState newState = this.state.next();
        if (!newState.equals(this.state)) {
            this.state = newState;
            System.out.println(this + " is improved. It is " + this.state + " now.");
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

