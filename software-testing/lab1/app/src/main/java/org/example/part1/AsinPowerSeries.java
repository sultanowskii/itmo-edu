package org.example.part1;

public class AsinPowerSeries {
    private long factorial(long n) {
        long res = 1;
        for (long i = 2; i <= n; i++) {
            res *= i;
        }
        return res;
    }

    public double asin(double x, int n) {
        if (x > 1 || x < -1) {
            return Double.NaN;
        }
        if (x == 0) {
            return 0;
        }

        double res = 0;

        // https://www.calc.ru/Ryad-Teylora-Ryady-Maklorena.html?print=1
        for (int k = 0; k <= n; k++) {
            double a1 = factorial(2 * k);
            double a2 = Math.pow(4, k) * Math.pow(factorial(k), 2);
            double a = a1 / a2;

            double b1 = Math.pow(x, 2 * k + 1);
            double b2 = 2 * k + 1;
            double b = b1 / b2;

            res += a * b;
        }

        return res;
    }
}
