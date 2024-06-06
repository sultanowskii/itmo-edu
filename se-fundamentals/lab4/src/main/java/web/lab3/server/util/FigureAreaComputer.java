package web.lab3.server.util;

public class FigureAreaComputer {
    public static double computeFigureArea(double r) {
        var squareArea = Math.pow(r, 2);
        var triangleArea = Math.pow(r / 2, 2) / 2;
        var circleArea = Math.PI * Math.pow(r, 2) / 4;

        return squareArea + triangleArea + circleArea;
    }
}
