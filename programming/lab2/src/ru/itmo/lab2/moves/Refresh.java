package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class Refresh extends StatusMove {
    public Refresh() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon att) {
        Status status = att.getCondition();
        if (status == Status.PARALYZE || status == Status.POISON || status == Status.BURN) {
            att.setCondition(new Effect());
        }
    }

    @Override
    protected String describe() {
        return "uses Refresh";
    }
}
