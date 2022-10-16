package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class RazorLeaf extends PhysicalMove {
    public RazorLeaf() {
        super(Type.GRASS, 55, 95);
    }

    @Override
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
        return Math.random() <= (1.0 / 8.0) ? 2 : 1;
    }

    @Override
    protected String describe() {
        return "uses Razor Leaf";
    }
}
