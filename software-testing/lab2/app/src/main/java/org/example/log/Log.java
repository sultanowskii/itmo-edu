package org.example.log;

import org.example.Func;

public class Log  implements Func {
    private int base;
    private Ln ln;

    public Log(Ln ln, int base) {
        if (base <= 0 || base == 1) {
            throw new ArithmeticException("invalid base");
        }
        this.ln = ln;
        this.base = base;
    }

    public Log(int base) {
        this(new Ln(), base);
    }

    @Override
    public double calc(double x, double precision) {
        if (x <= 0) {
            throw new ArithmeticException("invalid x value for log(x)");
        }
        return ln.calc(x, precision) / ln.calc(this.base, precision);
    }
}
