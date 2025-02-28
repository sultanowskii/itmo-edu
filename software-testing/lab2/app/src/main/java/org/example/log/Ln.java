package org.example.log;

import org.example.Func;

public class Ln implements Func {
    @Override
    public double calc(double x, double precision) {
        if (x <= 0) {
            throw new ArithmeticException("invalid x value for ln(x)");
        }
        if (x <= 2) {
            return doubleCalcWithinRange(x, precision);
        }
        return calc(x / 2, precision) + calc(2, precision);
    }

    private double doubleCalcWithinRange(double x, double precision) {
        double result = 0;
        int k = 1;
        double tmp = 0;
        double prev = Double.MAX_VALUE;

        while (Math.abs(tmp - prev) > precision) {
            prev = tmp;
            double a = Math.pow(-1, k + 1) / k;
            double b = Math.pow(x - 1, k);

            tmp = a * b;
            result += tmp;
            k++;
        }

        return result;
    }
}
