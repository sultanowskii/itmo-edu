package ru.itmo.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class HydroPump extends SpecialMove {
    public HydroPump() {
        super(Type.WATER, 110, 80);
    }

    protected String describe() {
        return "uses Hydro Pump";
    }
}
