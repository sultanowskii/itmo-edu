package ru.itmo.lab3.movement;

import ru.itmo.lab3.Location;

public class Burrow implements Movement {
    public void moveTo(Location destination) {
        System.out.println("burrows into " + destination + ".");
    }
}
