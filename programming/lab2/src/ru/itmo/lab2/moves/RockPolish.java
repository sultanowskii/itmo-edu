package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class RockPolish extends StatusMove {
    public RockPolish() {
        super(Type.ROCK, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon att) {
        att.setMod(Stat.SPEED, 2);
    }

    @Override
    protected String describe() {
        return "uses Rock Polish";
    }
}
