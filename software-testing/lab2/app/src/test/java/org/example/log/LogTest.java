package org.example.log;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogTest {
    @Test public void logReturnsCorrectResults() {
        record TestCase(int base, double x, double expected) {};
        final double PRECISION = 0.001;
        final double DELTA = 0.01;

        TestCase[] cases = {
            new TestCase(2, 1.0, 0.0),
            new TestCase(2, 8.0, 3.0),
            new TestCase(3, Math.E, 0.9102392266268373),
            new TestCase(10, 100.0, 2.0),
        };

        for (var v : cases) {
            Log ln = new Log(v.base);

            var actual = ln.calc(v.x, PRECISION);

            assertEquals("for x=" + v.x, v.expected, actual, DELTA);
        }
    }

    @Test public void logThrowsExceptionOnInvalidX() {
        var ln = new Log(10);
        assertThrows(ArithmeticException.class, () -> {
            ln.calc(-1, 0.0001);
        });
    }

    @Test public void logThrowsExceptionOnInvalidBase() {
        record TestCase(int base) {};

        TestCase[] cases = {
            new TestCase(-8),
            new TestCase(-1),
            new TestCase(0),
            new TestCase(1),
        };

        for (var v : cases) {
            assertThrows(
                "for base=" + v.base,
                ArithmeticException.class,
                () -> {
                    new Log(v.base);
            });
        }
    }
}