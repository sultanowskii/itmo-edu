package org.example.part3;

public class Robot extends Actor {
    @Override
    public String standup() {
        return this.name() + " stands up with effort";
    }

    @Override
    public String walkAcross(Place place) {
        return this.name() + " walks across " + place.name() + ". At least it seems like it";
    }

    @Override
    public String lookAt(Nameable other) {
        return this.name() + " looks at " + other.name() + ", which seems like it looks through its shoulder";
    }

    @Override
    public String name() {
        return "robot";
    }
}
