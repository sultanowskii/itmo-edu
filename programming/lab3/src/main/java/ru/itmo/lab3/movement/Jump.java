package ru.itmo.lab3.movement;

import ru.itmo.lab3.Location;

public class Jump implements Movement {
    public void moveTo(Location destination) {
        System.out.println("jumps onto " + destination + ".");
    }
}
