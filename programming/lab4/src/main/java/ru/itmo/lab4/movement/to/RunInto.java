package ru.itmo.lab4.movement.to;

import ru.itmo.lab4.Location;

public class RunInto implements MovementInto {
    @Override
    public void move(Location destination) {
        System.out.println("runs into " + destination + ".");
    }
}
