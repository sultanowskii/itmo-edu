package ru.itmo.lab2.pokemons;

import ru.ifmo.se.pokemon.*;
import ru.itmo.lab2.moves.Facade;
import ru.itmo.lab2.moves.Recover;
import ru.itmo.lab2.moves.FlameCharge;

public class Slugma extends Pokemon {
    public Slugma(String name, int level) {
        super(name, level);
        this.setType(Type.FIRE);
        this.setStats(40, 40, 40, 70, 40, 20);
        this.addMove(new Facade());
        this.addMove(new Recover());
        this.addMove(new FlameCharge());
    }
}
