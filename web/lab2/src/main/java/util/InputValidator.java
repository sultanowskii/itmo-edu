package util;

public class InputValidator {
    public static final int MIN_X = -4;
    public static final int MAX_X = 4;

    public static final double MIN_Y = -5;
    public static final double MAX_Y = 5;

    public static final int MIN_R = 1;
    public static final int MAX_R = 5;

    public static boolean isXValid(int x) {
        return MIN_X <= x && x <= MAX_X;
    }

    public static boolean isYValid(double y) {
        return MIN_Y <= y && y <= MAX_Y;
    }

    public static boolean isRValid(int r) {
        return MIN_R <= r && r <= MAX_R;
    }

    public static boolean isInputValid(int x, double y, int r) {
        return isXValid(x) && isYValid(y) && isRValid(r);
    }
}
