package org.example.part3;

public abstract class Actor implements Nameable, Moving {
    @Override
    public String standup() {
        return this.name() + " stands up";
    }

    @Override
    public String walkAcross(Place place) {
        return this.name() + " walks across " + place.name();
    }

    @Override
    public String stop() {
        return this.name() + " stops";
    }

    @Override
    public String stopInFrontOf(Nameable something) {
        return this.name() + " stops in front of " + something.name();
    }

    public String lookAt(Nameable other) {
        return this.name() + " looks at " + other.name();
    }

    @Override
    public abstract String name();
}
