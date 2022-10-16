package ru.itmo.lab2.pokemons;

import ru.itmo.lab2.moves.Psychic;

public class Florges extends Floette {
    public Florges(String name, int level) {
        super(name, level);
        this.setStats(78, 65, 68, 112, 154, 75);
        this.addMove(new Psychic());
    }
}
