package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class Recover extends StatusMove {
    public Recover() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon att) {
        double healthToAdjust = att.getHP() / 2;
        att.setMod(Stat.HP, (int) Math.round(-healthToAdjust));
    }

    @Override
    protected String describe() {
        return "uses Recover";
    }
}
