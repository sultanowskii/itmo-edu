package org.example.trig;

import static org.junit.Assert.*;

import org.junit.Test;

public class CosTest {
    @Test public void cosReturnsCorrectResults() {
        record TestCase(double x, double expected) {};
        final double PRECISION = 0.001;
        final double DELTA = 0.01;

        TestCase[] cases = {
            new TestCase(0, 1),
            new TestCase(Math.PI / 2, 0),
            new TestCase(-Math.PI / 2, 0),
            new TestCase(Math.PI, -1),
            new TestCase(-Math.PI, -1),
            new TestCase(0.5, 0.8775826),
            new TestCase(-0.5, 0.8775826),
            new TestCase(100, 0.8623188722876839),
            new TestCase(-100, 0.8623188722876839),
        };

        for (var v : cases) {
            var cos = new Cos();
    
            var actual = cos.calc(v.x, PRECISION);

            assertEquals("for x=" + v.x, v.expected, actual, DELTA);
        }
    }
}