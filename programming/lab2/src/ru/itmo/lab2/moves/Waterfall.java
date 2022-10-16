package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class Waterfall extends PhysicalMove {
    public Waterfall() {
        super(Type.WATER, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon def) {
        if (0.2 <= Math.random()) {
            Effect.flinch(def);
        }
    }

    @Override
    protected String describe() {
        return "uses Waterfall";
    }
}
