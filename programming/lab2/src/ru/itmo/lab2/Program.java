package ru.itmo.lab2;

import ru.ifmo.se.pokemon.*;
import ru.itmo.lab2.pokemons.*;

public class Program {
    public static void main(String[] args) {
        Battle b = new Battle();

        Pokemon p1 = new Cosmog("Peter", 1);
        Pokemon p2 = new Slugma("Stephen", 1);
        Pokemon p3 = new Magcargo("Alex", 1);
        Pokemon p4 = new Flabebe("Dean", 1);
        Pokemon p5 = new Floette("Charlie", 1);
        Pokemon p6 = new Florges("Mike", 1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);

        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);

        b.go();
    }
}
