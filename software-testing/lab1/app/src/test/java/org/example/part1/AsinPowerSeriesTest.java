package org.example.part1;

import static org.junit.Assert.*;

import org.junit.Test;

public class AsinPowerSeriesTest {
    @Test public void asinReturnsCorrectResults() {
        record TestCase(double x, double expected) {};
        final double DELTA = 0.2;
        final int N = 50;

        TestCase[] cases = {
            new TestCase(0, 0),
            new TestCase(0.3, 0.304693),
            new TestCase(-0.3, -0.304693),
            new TestCase(1, 1.57079632),
            new TestCase(-1, -1.57079632),
        };

        for (var v : cases) {
            AsinPowerSeries asinPowerSeries = new AsinPowerSeries();
    
            var actual = asinPowerSeries.asin(v.x, N);

            assertEquals("for x=" + v.x, v.expected, actual, DELTA);
        }
    }

    @Test public void asinReturnsNanOnInvalidArgs() {
        record TestCase(double x) {};

        TestCase[] cases = {
            new TestCase(-1.0001),
            new TestCase(1.0001),
            new TestCase(-10000000),
            new TestCase(10000000),
        };

        for (var v : cases) {
            AsinPowerSeries asinPowerSeries = new AsinPowerSeries();
    
            var actual = asinPowerSeries.asin(v.x, 50);

            assertEquals("for x=" + v.x, Double.NaN, actual, 0);
        }
    }
}
