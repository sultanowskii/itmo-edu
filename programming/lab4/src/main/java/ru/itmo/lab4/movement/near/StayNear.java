package ru.itmo.lab4.movement.near;

import ru.itmo.lab4.Location;

public class StayNear implements MovementNear {
    public void move(Location nearby) {
        System.out.println("stays near " + nearby + ".");
    }
}
