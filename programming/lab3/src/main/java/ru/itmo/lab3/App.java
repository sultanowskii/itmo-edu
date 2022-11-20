package ru.itmo.lab3;

import ru.itmo.lab3.events.Event;
import ru.itmo.lab3.mental.CryLevel;
import ru.itmo.lab3.mental.Memory;
import ru.itmo.lab3.movement.Burrow;
import ru.itmo.lab3.movement.Jump;
import ru.itmo.lab3.movement.Run;

public class App {
    public static void main(String[] args) {
        Location pillow = new Location("Pillow");
        Location bed = new Location("Bed");
        Location room = new Location("Malysh's room");
        bed.addInnerLocation(pillow);
        room.addInnerLocation(bed);

        Human malysh = new Human("Malysh", CryLevel.TREMBLING);
        malysh.addMemory(new Memory("Malysh accepted the fact that he might not get the dog"));
        malysh.addMemory(new Memory("Malysh decided to stay positive"));
        malysh.addMemory(new Memory("Malysh is received plushy toy dog"));
        Human bosse = new Human("Bosse");
        Human bettan = new Human("Bettan");
        Human mother = new Human("Mother");

        Event birthday = new Event("Birthday");

        malysh.moveTo(new Run(), room);
        malysh.moveTo(new Jump(), bed);

        bosse.moveTo(new Run(), room);
        bettan.moveTo(new Run(), room);

        mother.moveTo(new Run(), room);

        malysh.cry();

        birthday.worsen();

        while (malysh.getMemoriesLeftCount() > 0) {
            malysh.rememberNextMemoryDetails();
        }

        malysh.increaseCryLevel();
        malysh.cry();
        malysh.moveTo(new Burrow(), pillow);
    }
}
