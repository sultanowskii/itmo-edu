package ru.itmo.lab2.pokemons;

import ru.itmo.lab2.moves.RazorLeaf;

public class Floette extends Flabebe {
    public Floette(String name, int level) {
        super(name, level);
        this.setStats(54, 45, 47, 75, 98, 52);
        this.addMove(new RazorLeaf());
    }
}
