package util;

public class AreaChecker {
    private static boolean checkRect(int x, double y, int r) {
        boolean isXWithin = -r <= x && x <= 0;
        boolean isYWithin = -((double)r / 2) <= y && y <= 0;

        return isXWithin && isYWithin;
    }

    private static boolean checkTriangle(int x, double y, int r) {
        boolean isXWithin = -(r / 2) <= x && x <= 0;
        boolean isYWithin = 0 <= y && y <= ((double)r / 2);

        boolean isInsideTriangle = (-x + y) <= ((double)r / 2);

        return isXWithin && isYWithin && isInsideTriangle;
    }

    private static boolean checkCircle(int x, double y, int r) {
        boolean isXWithin = 0 <= x && x <= r;
        boolean isYWithin = 0 <= y && y <= r;

        boolean isInsideCirle = (x * x + y * y) <= (r * r);

        return isXWithin && isYWithin && isInsideCirle;
    }

    public static boolean checkArea(int x, double y, int r) {
        return checkRect(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r);
    }
}
