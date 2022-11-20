package ru.itmo.lab3.movement;

import ru.itmo.lab3.Location;

public class Run implements Movement {
    @Override
    public void moveTo(Location destination) {
        System.out.println("runs into " + destination + ".");
    }
}
