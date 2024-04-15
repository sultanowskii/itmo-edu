package web.lab4.server.web.pointCheck;

import jakarta.validation.constraints.*;

public class Point {
    @NotNull(message="X required")
    @Min(value=-3, message="X must be in range [-3; 5]")
    @Max(value=5, message="X must be in range [-3; 5]")
    private int x;

    @NotNull(message="Y required")
    private double y;

    @NotNull(message="R required")
    @Min(value=-3, message="R must be in range [-3; 5]")
    @Max(value=5, message="R must be in range [-3; 5]")
    private int r;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}
