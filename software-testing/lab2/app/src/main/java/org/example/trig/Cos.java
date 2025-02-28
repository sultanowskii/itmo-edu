package org.example.trig;

import org.example.Func;

public class Cos implements Func {
    private Func sin;

    public Cos() {
        this.sin = new Sin();
    }

    public Cos(Func sin) {
        this.sin = sin;
    }

    @Override
    public double calc(double x, double precision) {
        return sin.calc(Math.PI / 2 + x, precision);
    }
}
