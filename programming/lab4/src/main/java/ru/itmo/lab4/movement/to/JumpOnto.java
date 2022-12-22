package ru.itmo.lab4.movement.to;

import ru.itmo.lab4.Location;
import ru.itmo.lab4.movement.to.MovementOnto;

public class JumpOnto implements MovementOnto {
    public void move(Location destination) {
        System.out.println("jumps onto " + destination + ".");
    }
}
