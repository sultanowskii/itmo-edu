package org.example;

import java.util.HashMap;
import java.util.Map;

public class TableFunc implements Func {
    private String name;
    private Map<Double, Double> expectedRuns;    

    public TableFunc(String name, FuncRun... expectedFuncRuns) {
        this.name = name;

        this.expectedRuns = new HashMap<>();
        for (var er : expectedFuncRuns) {
            expectedRuns.put(er.x(), er.y());
        }
    }

    @Override
    public double calc(double x, double precision) throws AssertionError {
        Double v = this.expectedRuns.get(x);

        if (v == null) {
            throw new AssertionError("unexpected call " + this.name + "(" + x + ")");
        }

        return v;
    }
}
