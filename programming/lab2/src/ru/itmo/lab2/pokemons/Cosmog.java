package ru.itmo.lab2.pokemons;

import ru.ifmo.se.pokemon.*;
import ru.itmo.lab2.moves.Confide;
import ru.itmo.lab2.moves.Waterfall;
import ru.itmo.lab2.moves.HydroPump;
import ru.itmo.lab2.moves.Refresh;

public class Cosmog extends Pokemon {
    public Cosmog(String name, int level) {
        super(name, level);
        this.setType(Type.PSYCHIC);
        this.setStats(43, 29, 31, 29, 31, 37);
        this.addMove(new Confide());
        this.addMove(new Waterfall());
        this.addMove(new HydroPump());
        this.addMove(new Refresh());
    }
}
