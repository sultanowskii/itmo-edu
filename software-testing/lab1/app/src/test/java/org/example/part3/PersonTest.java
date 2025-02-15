package org.example.part3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {
    @Test public void personNamesItselfByProvidedName() {
        Person alice = new Person("Alice", 46);

        assertEquals("Alice", alice.name());
    }

    @Test public void personTellsAProvidedAge() {
        Person alice = new Person("Alice", 46);

        assertEquals(46, alice.age());
    }

    @Test public void personIntroducesThemSelvesProperly() {
        Person alice = new Person("Alice", 46);

        assertEquals("My name is Alice, I'm 46 years old", alice.introduction());
    }
}
