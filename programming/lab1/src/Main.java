import java.util.HashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int X_LENGTH = 14;
        final double X_MIN_VALUE = -11.0;
        final double X_MAX_VALUE = 14.0;
        final int F_SIZE_1 = 13;
        final int F_SIZE_2 = 14;
        HashSet<Short> F_CONDITION_SET_1 = new HashSet<Short>();
        F_CONDITION_SET_1.add((short)4);
        F_CONDITION_SET_1.add((short)7);
        F_CONDITION_SET_1.add((short)9);
        F_CONDITION_SET_1.add((short)13);
        F_CONDITION_SET_1.add((short)14);
        F_CONDITION_SET_1.add((short)15);

        Random random = new Random();

        short[] p = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3};

        double[] x = new double[X_LENGTH];
        for (int i = 0; i < X_LENGTH; i++) {
            x[i] = random.nextDouble() * (X_MAX_VALUE - X_MIN_VALUE) + X_MIN_VALUE;
        }

        double f[][] = new double[F_SIZE_1][F_SIZE_2];

        for (int i = 0; i < F_SIZE_1; i++) {
            for (int j = 0; j < F_SIZE_2; j++) {
                double tmp = Math.atan(Math.pow((x[j] + 1.5) / 25, 2));
                if (p[i] == 5) {
                    f[i][j] = Math.pow(tmp / Math.cos(Math.atan((x[j] + 1.5) / 25)) + 0.25, 2);
                }
                else if (F_CONDITION_SET_1.contains(p[i])) {
                    f[i][j] = tmp;
                }
                else {
                    f[i][j] = Math.pow(1 - Math.pow(2 * Math.log(5 + Math.abs(x[j])), Math.atan(Math.sin(x[j]))), Math.log(Math.sqrt(Math.sqrt(Math.abs(x[j])))));
                }
            }
        }

        for (double[] f_1 : f) {
            for (double number : f_1) {
                System.out.printf("%.2f ", number);
            }
            System.out.println();
        }
    }
}
