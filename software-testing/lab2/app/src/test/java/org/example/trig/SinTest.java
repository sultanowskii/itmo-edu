package org.example.trig;

import static org.junit.Assert.*;

import org.junit.Test;

public class SinTest {
    @Test public void sinReturnsCorrectResults() {
        record TestCase(double x, double expected) {};
        final double PRECISION = 0.001;
        final double DELTA = 0.01;

        TestCase[] cases = {
            new TestCase(0, 0),
            new TestCase(Math.PI / 2, 1),
            new TestCase(-Math.PI / 2, -1),
            new TestCase(Math.PI, 0),
            new TestCase(-Math.PI, 0),
            new TestCase(0.5, 0.479),
            new TestCase(-0.5, -0.479),
            new TestCase(100, -0.506365),
            new TestCase(-100, 0.506365),
        };

        for (var v : cases) {
            Sin sin = new Sin();
    
            var actual = sin.calc(v.x, PRECISION);

            assertEquals("for x=" + v.x, v.expected, actual, DELTA);
        }
    }
}