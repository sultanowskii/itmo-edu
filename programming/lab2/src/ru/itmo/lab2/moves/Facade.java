package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 70, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        Status status = def.getCondition();
        double damageToDeal = damage;

        if (status == Status.BURN || status == Status.POISON || status == Status.PARALYZE) {
            damageToDeal *= 2;
        }

        def.setMod(Stat.HP, (int) Math.round(damageToDeal));
    }

    @Override
    protected String describe() {
        return "uses Facade";
    }
}
