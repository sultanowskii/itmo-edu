package org.example.part3;

public interface Moving {
    String walkAcross(Place place);
    String stop();
    String stopInFrontOf(Nameable something);
    String standup();
}
