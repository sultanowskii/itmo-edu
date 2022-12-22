package ru.itmo.lab4.movement.from;

import ru.itmo.lab4.Location;
import ru.itmo.lab4.movement.to.MovementTo;

public class BreakOutFrom implements MovementTo {
    public void move(Location source) {
        System.out.println("breaks out from " + source + ".");
    }
}
