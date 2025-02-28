package org.example.log;

import static org.junit.Assert.*;

import org.junit.Test;

public class LnTest {
    @Test public void lnReturnsCorrectResults() {
        record TestCase(double x, double expected) {};
        final double PRECISION = 0.001;
        final double DELTA = 0.01;

        TestCase[] cases = {
            new TestCase(1, 0),
            new TestCase(2, 0.69315),
            new TestCase(Math.E, 1),
            new TestCase(10, 2.30258),
            new TestCase(100, 4.60517),
            new TestCase(0.3, -1.20397),
        };

        for (var v : cases) {
            Ln ln = new Ln();
    
            var actual = ln.calc(v.x, PRECISION);

            assertEquals("for x=" + v.x, v.expected, actual, DELTA);
        }
    }

    @Test public void lnThrowsExceptionOnInvalidX() {
        Ln ln = new Ln();
        assertThrows(ArithmeticException.class, () -> {
            ln.calc(-1, 0.0001);
        });
    }
}