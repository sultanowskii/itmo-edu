package org.example.part3;

import static org.junit.Assert.*;

import org.junit.Test;

public class RobotTest {
    @Test public void robotNamesItselfRobot() {
        Robot robot = new Robot();

        assertEquals("robot", robot.name());
    }

    @Test public void robotLooksAt() {
        Robot robot = new Robot();
        Person person = new Person("Peter", 37);

        assertEquals("robot looks at Peter, which seems like it looks through its shoulder", robot.lookAt(person));
    }

    @Test public void robotWalksAcrossProperly() {
        Robot robot = new Robot();
        Place field = new Place("field", 100, 100);

        assertEquals("robot walks across field (place). At least it seems like it", robot.walkAcross(field));
    }

    @Test public void robotStandsUpWithEffort() {
        Robot robot = new Robot();

        assertEquals("robot stands up with effort", robot.standup());
    }
}
