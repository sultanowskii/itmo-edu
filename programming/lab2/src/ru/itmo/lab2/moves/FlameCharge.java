package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class FlameCharge extends PhysicalMove {
    public FlameCharge() {
        super(Type.FIRE, 50, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon att) {
        att.setMod(Stat.SPEED, 1);
    }

    @Override
    protected String describe() {
        return "uses Flame Charge";
    }
}
