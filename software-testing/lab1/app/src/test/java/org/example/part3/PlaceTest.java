package org.example.part3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlaceTest {
    @Test public void placeHasANameWithSuffix() {
        Place garden = new Place("garden", 123, 456);

        assertEquals("garden (place)", garden.name());
    }

    @Test public void placeReturnsProvidedDimensions() {
        Place garden = new Place("garden", 123, 456);

        assertEquals(123, garden.getWidth());
        assertEquals(456, garden.getHeight());
    }

    @Test public void placeAreaIsCalculatedAsProperRect() {
        record TestCase(int width, int height, int expected) {};

        TestCase[] cases = {
            new TestCase(1, 1, 1),
            new TestCase(2, 1, 2),
            new TestCase(1, 2, 2),
            new TestCase(2, 2, 4),
            new TestCase(0, 1, 0),
            new TestCase(1, 0, 0),
            new TestCase(0, 0, 0),
        };

        for (var v : cases) {
            Place garden = new Place("garden", v.width, v.height);
    
            assertEquals(
                "with width=" + v.width + ", height=" + v.height,
                v.expected,
                garden.area()
            );
        }
    }
}
