package ru.itmo.lab4.movement.to;

import ru.itmo.lab4.Location;

public interface MovementOnto extends MovementTo {
    void move(Location destination);
}
