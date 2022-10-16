package ru.itmo.lab2.pokemons;

import ru.ifmo.se.pokemon.*;
import ru.itmo.lab2.moves.Psychic;
import ru.itmo.lab2.moves.Confide;

public class Flabebe extends Pokemon {
    public Flabebe(String name, int level) {
        super(name, level);
        this.setType(Type.FAIRY);
        this.setStats(44, 38, 39, 61, 79, 42);
        this.addMove(new Psychic());
        this.addMove(new Confide());
    }
}
