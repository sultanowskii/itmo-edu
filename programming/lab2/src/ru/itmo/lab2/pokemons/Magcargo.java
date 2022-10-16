package ru.itmo.lab2.pokemons;

import ru.ifmo.se.pokemon.*;
import ru.itmo.lab2.moves.RockPolish;

public class Magcargo extends Slugma {
    public Magcargo(String name, int level) {
        super(name, level);
        this.addType(Type.ROCK);
        this.setStats(60, 50, 120, 90, 80, 30);
        this.addMove(new RockPolish());
    }
}
