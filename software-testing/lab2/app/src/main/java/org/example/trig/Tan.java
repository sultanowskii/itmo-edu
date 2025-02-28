package org.example.trig;

import org.example.Func;

public class Tan implements Func {
    private Func sin;
    private Func cos;

    public Tan(Func sin, Func cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public Tan() {
        this.sin = new Sin();
        this.cos = new Cos();
    }

    @Override
    public double calc(double x, double precision) {
        return sin.calc(x, precision) / cos.calc(x, precision);
    }
}
