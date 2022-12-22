package ru.itmo.lab4.movement.to;

import ru.itmo.lab4.Location;

public class BurrowInto implements MovementInto {
    public void move(Location destination) {
        System.out.println("burrows into " + destination + ".");
    }
}
