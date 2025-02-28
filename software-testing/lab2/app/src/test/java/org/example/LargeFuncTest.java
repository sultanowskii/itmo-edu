package org.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.junit.Test;

public class LargeFuncTest {
    @Test public void largeFuncReturnsCorrectResults() {
        record TestCase(Supplier<LargeFunc> largeFuncBuilder, double x, double expected) {};
        final double PRECISION = 0.0001;
        final double DELTA = 0.001;

        TestCase[] cases = {
            new TestCase(
                () -> {
                    Func sin = mock(Func.class);
                    Func cos = mock(Func.class);
                    Func tan = mock(Func.class);
                    Func csc = mock(Func.class);
                    Func ln = mock(Func.class);
                    when(ln.calc(eq(1), anyDouble())).thenReturn(0.0);
                    Func log2 = mock(Func.class);
                    when(log2.calc(eq(1), anyDouble())).thenReturn(0.0);
                    Func log3 = mock(Func.class);
                    when(log3.calc(eq(1), anyDouble())).thenReturn(0.0);
                    Func log5 = mock(Func.class);
                    when(log5.calc(eq(1), anyDouble())).thenReturn(0.0);
                    Func log10 = mock(Func.class);
                    when(log10.calc(eq(1), anyDouble())).thenReturn(0.0);
                    return new LargeFunc(sin, cos, tan, csc, ln, log2, log3, log5, log10);
                },
                1,
                0
            ),
            new TestCase(
                () -> {
                    Func sin = mock(Func.class);
                    Func cos = mock(Func.class);
                    Func tan = mock(Func.class);
                    Func csc = mock(Func.class);
                    Func ln = mock(Func.class);
                    when(ln.calc(eq(2.0), anyDouble())).thenReturn(0.6931471805599453);
                    Func log2 = mock(Func.class);
                    when(log2.calc(eq(2.0), anyDouble())).thenReturn(1.0);
                    Func log3 = mock(Func.class);
                    when(log3.calc(eq(2.0), anyDouble())).thenReturn(0.6309297535714574);
                    Func log5 = mock(Func.class);
                    when(log5.calc(eq(2.0), anyDouble())).thenReturn(0.43067655807339306);
                    Func log10 = mock(Func.class);
                    when(log10.calc(eq(2.0), anyDouble())).thenReturn(0.30102999566398114);
                    return new LargeFunc(sin, cos, tan, csc, ln, log2, log3, log5, log10);
                },
                2.0,
                0.05058
            ),
            new TestCase(
                () -> {
                    Func sin = mock(Func.class);
                    Func cos = mock(Func.class);
                    Func tan = mock(Func.class);
                    Func csc = mock(Func.class);
                    Func ln = mock(Func.class);
                    when(ln.calc(eq(10.0), anyDouble())).thenReturn(2.302585092994046);
                    Func log2 = mock(Func.class);
                    when(log2.calc(eq(10.0), anyDouble())).thenReturn(3.3219280948873626);
                    Func log3 = mock(Func.class);
                    when(log3.calc(eq(10.0), anyDouble())).thenReturn(2.095903274289385);
                    Func log5 = mock(Func.class);
                    when(log5.calc(eq(10.0), anyDouble())).thenReturn(1.4306765580733933);
                    Func log10 = mock(Func.class);
                    when(log10.calc(eq(10.0), anyDouble())).thenReturn(1.0);
                    return new LargeFunc(sin, cos, tan, csc, ln, log2, log3, log5, log10);
                },
                10.0,
                38.22308
            ),
            new TestCase(
                () -> {
                    Func sin = mock(Func.class);
                    when(sin.calc(eq(-1.0), anyDouble())).thenReturn(-0.8414709848078965);
                    Func cos = mock(Func.class);
                    when(cos.calc(eq(-1.0), anyDouble())).thenReturn(0.5403023058681398);
                    Func tan = mock(Func.class);
                    when(tan.calc(eq(-1.0), anyDouble())).thenReturn(-1.5574077246549023);
                    Func csc = mock(Func.class);
                    when(csc.calc(eq(-1.0), anyDouble())).thenReturn(-1.1883951057781212);
                    Func ln = mock(Func.class);
                    Func log2 = mock(Func.class);
                    Func log3 = mock(Func.class);
                    Func log5 = mock(Func.class);
                    Func log10 = mock(Func.class);
                    return new LargeFunc(sin, cos, tan, csc, ln, log2, log3, log5, log10);
                },
                -1.0,
                -0.26397
            ),
            new TestCase(
                () -> {
                    Func sin = mock(Func.class);
                    when(sin.calc(eq(-0.833373), anyDouble())).thenReturn(-0.7402035249660651);
                    Func cos = mock(Func.class);
                    when(cos.calc(eq(-0.833373), anyDouble())).thenReturn(0.6723828832055526);
                    Func tan = mock(Func.class);
                    when(tan.calc(eq(-0.833373), anyDouble())).thenReturn(-1.1008661039037475);
                    Func csc = mock(Func.class);
                    when(csc.calc(eq(-0.833373), anyDouble())).thenReturn(-1.3509797863308546);
                    Func ln = mock(Func.class);
                    Func log2 = mock(Func.class);
                    Func log3 = mock(Func.class);
                    Func log5 = mock(Func.class);
                    Func log10 = mock(Func.class);
                    return new LargeFunc(sin, cos, tan, csc, ln, log2, log3, log5, log10);
                },
                -0.833373,
                0
            ),
        };

        for (var v : cases) {
            var lf = v.largeFuncBuilder.get();

            var actual = lf.calc(v.x, PRECISION);

            assertEquals("for x=" + v.x, v.expected, actual, DELTA);
        }

    }
}

