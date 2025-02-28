package org.example;

import java.util.Arrays;

import org.example.log.Ln;
import org.example.log.Log;
import org.example.trig.Cos;
import org.example.trig.Csc;
import org.example.trig.Sin;
import org.example.trig.Tan;

public class App {
    final static double PRECISION = 0.001;

    private static void runAndWrite(String name, Func f, double... xs) {
        CSV.writeCSV(
            name + ".csv",
            Arrays
                .stream(xs)
                .mapToObj(x -> {
                    return new FuncRun(x, f.calc(x, PRECISION));
                })
                .toList()
        );
    }

    public static void main(String[] args) {
        System.out.println("ok");

        runAndWrite(
            "sin",
            new Sin(),
            0.0,
            Math.PI / 2,
            -Math.PI / 2,
            Math.PI,
            -Math.PI
        );

        runAndWrite(
            "cos",
            new Cos(),
            0.0,
            Math.PI / 2,
            -Math.PI / 2,
            Math.PI,
            -Math.PI
        );

        runAndWrite(
            "csc",
            new Csc(),
            Math.PI / 2,
            -Math.PI / 2,
            Math.PI,
            -Math.PI
        );

        runAndWrite(
            "tan",
            new Tan(),
            0.0,
            Math.PI,
            -Math.PI
        );

        runAndWrite(
            "ln",
            new Ln(),
            0.5,
            1.0,
            2,
            Math.E,
            3
        );

        runAndWrite(
            "log2",
            new Log(2),
            1.0,
            2,
            Math.E,
            4,
            8
        );

        runAndWrite(
            "func",
            new LargeFunc(),
            1.0,
            1.04,
            2,
            Math.E,
            3,
            10,
            -0.87174,
            -2.22608,
            -7.15493
        );
    }
}
