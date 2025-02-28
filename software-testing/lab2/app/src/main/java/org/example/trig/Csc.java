package org.example.trig;

import org.example.Func;

public class Csc implements Func {
    private Func sin;

    public Csc() {
        this.sin = new Sin();
    }

    public Csc(Func sin) {
        this.sin = sin;
    }

    @Override
    public double calc(double x, double precision) {
        return 1 / sin.calc(x, precision);
    }
}
