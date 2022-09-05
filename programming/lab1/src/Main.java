import java.util.HashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int xLength = 14;
        final double xMinValue = -11.0;
        final double xMaxValue = 14.0;
        final int fSize1 = 13;
        final int fSize2 = 14;
        final HashSet<Short> fConditionSet1 = new HashSet<Short>();
        fConditionSet1.add((short) 4);
        fConditionSet1.add((short) 7);
        fConditionSet1.add((short) 9);
        fConditionSet1.add((short) 13);
        fConditionSet1.add((short) 14);
        fConditionSet1.add((short) 15);

        Random random = new Random();

        short[] p = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3};

        double[] x = new double[xLength];
        for (int i = 0; i < xLength; i++) {
            x[i] = random.nextDouble() * (xMaxValue - xMinValue) + xMinValue;
        }

        double f[][] = new double[fSize1][fSize2];

        for (int i = 0; i < fSize1; i++) {
            for (int j = 0; j < fSize2; j++) {
                double tmp = Math.atan(Math.pow((x[j] + 1.5) / 25, 2));
                if (p[i] == 5) {
                    f[i][j] = Math.pow(tmp / Math.cos(Math.atan((x[j] + 1.5) / 25)) + 0.25, 2);
                } else if (fConditionSet1.contains(p[i])) {
                    f[i][j] = tmp;
                } else {
                    f[i][j] = Math.pow(1 - Math.pow(2 * Math.log(5 + Math.abs(x[j])), Math.atan(Math.sin(x[j]))), Math.log(Math.sqrt(Math.sqrt(Math.abs(x[j])))));
                }
            }
        }

        for (double[] row : f) {
            for (double element : row) {
                System.out.printf("%6.2f", element);
            }
            System.out.println();
        }
    }
}
