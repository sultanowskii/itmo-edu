package org.example.trig;

import org.example.Func;

public class Sin implements Func {
    private long factorial(long n) {
        long res = 1;
        for (long i = 2; i <= n; i++) {
            res *= i;
        }
        return res;
    }

    // http://neerc.ifmo.ru/wiki/index.php?title=%D0%A0%D0%B0%D0%B7%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5_%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B9_%D0%B2_%D1%81%D1%82%D0%B5%D0%BF%D0%B5%D0%BD%D0%BD%D1%8B%D0%B5_%D1%80%D1%8F%D0%B4%D1%8B&mobileaction=toggle_view_desktop
    @Override
    public double calc(double x, double precision) {
        double result = 0;
        double tmp = 0;
        double prev = Double.MAX_VALUE;
        int k = 0;

        final double PI2 = Math.PI * 2;

        while (x > PI2) {
            x -= PI2;
        }
        while (x < -PI2) {
            x += PI2;
        }

        while (Math.abs(tmp - prev) > precision) {
            prev = tmp;
            double a = Math.pow(-1, k);
            double b = Math.pow(x, 2 * k + 1);
            double c = factorial(2 * k + 1);

            tmp = a * (b / c);

            result += tmp;
            k++;
        }

        return result;
    }
}
