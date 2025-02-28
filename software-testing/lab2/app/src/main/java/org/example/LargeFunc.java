package org.example;

import org.example.log.Ln;
import org.example.log.Log;
import org.example.trig.Cos;
import org.example.trig.Csc;
import org.example.trig.Sin;
import org.example.trig.Tan;

public class LargeFunc implements Func {
    public static Object out;
    Func sin;
    Func cos;
    Func tan;
    Func csc;
    Func ln;
    Func log2;
    Func log3;
    Func log5;
    Func log10;

    public LargeFunc() {
        this.sin = new Sin();
        this.cos = new Cos();
        this.tan = new Tan();
        this.csc = new Csc();
        this.ln = new Ln();
        this.log2 = new Log(2);
        this.log3 = new Log(3);
        this.log5 = new Log(5);
        this.log10 = new Log(10);
    }

    public LargeFunc(Func sin, Func cos, Func tan, Func csc, Func ln, Func log2, Func log3, Func log5, Func log10) {
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.csc = csc;
        this.ln = ln;
        this.log2 = log2;
        this.log3 = log3;
        this.log5 = log5;
        this.log10 = log10;
    }

    @Override
    public double calc(double x, double precision) {
        if (x <= 0) {
            return calcLeqZero(x, precision);
        }
        return calcGtZero(x, precision);
    }

    private double calcLeqZero(double x, double precision) {
        return Math.pow(
            Math.pow(
                cos.calc(x, precision)
                /
                tan.calc(x, precision),
                2
            )
            /
            (
                cos.calc(x, precision)
                /
                (
                    sin.calc(x, precision)
                    +
                    sin.calc(x, precision)
                )
            ),
            3
        )
        +
        (
            (
                Math.pow(cos.calc(x, precision), 3)
                +
                tan.calc(x, precision)
            )
            -
            csc.calc(x, precision)
        );
    }

    private double calcGtZero(double x, double precision) {
        return (
            (
                (
                    (
                        log5.calc(x, precision)
                        +
                        log5.calc(x, precision)
                    )
                    *
                    log3.calc(x, precision)
                )
                -
                log10.calc(x, precision)
            )
            *
            (ln.calc(x, precision) * log2.calc(x, precision))
        )
        *
        log10.calc(x, precision);
    }
}
