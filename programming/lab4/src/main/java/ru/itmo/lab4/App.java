package ru.itmo.lab4;

import ru.itmo.lab4.events.Event;
import ru.itmo.lab4.events.exception.NotImprovableEventException;
import ru.itmo.lab4.human.Human;
import ru.itmo.lab4.human.HumanGroup;
import ru.itmo.lab4.mental.CryLevel;
import ru.itmo.lab4.mental.Memory;
import ru.itmo.lab4.mental.MoodLevel;
import ru.itmo.lab4.mental.Thought;
import ru.itmo.lab4.movement.from.BreakOutFrom;
import ru.itmo.lab4.movement.near.StayNear;
import ru.itmo.lab4.movement.to.BurrowInto;
import ru.itmo.lab4.movement.to.JumpOnto;
import ru.itmo.lab4.movement.to.RunInto;


public class App {
    public static void main(String[] args) {
        class EventImprovementWay {
            private final String description;

            EventImprovementWay(String description) {
                this.description = description;
            }

            String getDescription() {
                return this.description;
            }
        }

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

        Event birthday = new Event("Birthday"){
            @Override
            public void improve(String wayToImprove) {
                throw new NotImprovableEventException("There is nothing that can help to improve " + this);
            }
        };

        bettan.hug(malysh);

        Location bettan_hands = new Location("Bettan's hands");
        malysh.move(new BreakOutFrom(), bettan_hands);

        malysh.move(new RunInto(), room);
        malysh.move(new JumpOnto(), bed);

        bosse.move(new RunInto(), room);
        bettan.move(new RunInto(), room);

        mother.move(new RunInto(), room);

        malysh.cry();

        birthday.worsen();

        while (malysh.getMemoriesLeftCount() > 0) {
            malysh.rememberNextMemoryDetails();
        }

        malysh.increaseCryLevel();
        malysh.cry();
        malysh.move(new BurrowInto(), pillow);

        HumanGroup siblings_groups = new HumanGroup("");
        siblings_groups.addHuman(mother);
        siblings_groups.addHuman(bosse);
        siblings_groups.addHuman(bettan);

        siblings_groups.move(new StayNear(), bed);

        siblings_groups.feel(MoodLevel.SAD);
        malysh.cry();

        malysh.think(new Thought("What's the matter, if the father comes home?"));
        malysh.think(new Thought("Everything is hopelessly sad."));

        EventImprovementWay birthdayImprovementWay = new EventImprovementWay("There is gotta be way...");
        birthday.improve(birthdayImprovementWay.getDescription());
    }
}
