package ru.itmo.lab4.movement.to;

import ru.itmo.lab4.Location;
import ru.itmo.lab4.movement.Movement;

public interface MovementTo extends Movement {
    void move(Location destination);
}
