package client.gui.util;

import java.awt.*;
import java.util.Random;

public class RandomColorGenerator {
    private static final Random random = new Random();

    public static Color generateRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }
}
