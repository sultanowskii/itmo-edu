package ru.itmo.lab4.movement.to;

import ru.itmo.lab4.Location;
import ru.itmo.lab4.movement.to.MovementTo;

public interface MovementInto extends MovementTo {
    void move(Location destination);
}
