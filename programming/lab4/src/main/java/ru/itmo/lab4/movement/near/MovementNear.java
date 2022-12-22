package ru.itmo.lab4.movement.near;

import ru.itmo.lab4.Location;
import ru.itmo.lab4.movement.Movement;

public interface MovementNear extends Movement {
    void move(Location nearby);
}
