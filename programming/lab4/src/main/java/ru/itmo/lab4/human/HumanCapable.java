package ru.itmo.lab4.human;

import ru.itmo.lab4.Entity;
import ru.itmo.lab4.Location;
import ru.itmo.lab4.action.Hugging;
import ru.itmo.lab4.mental.*;
import ru.itmo.lab4.mental.exception.NoMemoriesLeftException;
import ru.itmo.lab4.movement.Movement;

import java.util.ArrayList;
import java.util.List;

public abstract class HumanCapable extends Entity implements Crying, Remembering, Hugging<HumanCapable>, HasMood, Thinking {
    protected CryLevel cryLevel;
    protected final List<Memory> memories;
    protected int nextMemoryIndex;
    public HumanCapable(String name, CryLevel cryLevel) {
        super(name);
        this.cryLevel = cryLevel;
        this.memories = new ArrayList<>();
        this.nextMemoryIndex = 0;
    }

    public HumanCapable(String name) {
        this(name, CryLevel.NOT_CRYING);
    }

    @Override
    public void cry() {
        System.out.println(this + " cries (" + this.cryLevel + ").");
    }

    @Override
    public void decreaseCryLevel() {
        CryLevel prevCryLevel = cryLevel.previous();
        if (prevCryLevel == this.cryLevel) return;

        System.out.println(this + "'s cry level dropped down to " + prevCryLevel + ".");
        this.cryLevel = prevCryLevel;
    }

    @Override
    public void increaseCryLevel() {
        CryLevel nextCryLevel = cryLevel.next();
        if (nextCryLevel == this.cryLevel) return;


        System.out.println(this + "'s cry level rose up to " + nextCryLevel + ".");
        this.cryLevel = nextCryLevel;
    }

    public void move(Movement movement, Location location) {
        System.out.print(this + " ");
        movement.move(location);
    }

    public void feel(MoodLevel moodLevel) {
        System.out.println(this + " is " + moodLevel + ".");
    }

    public void think(Thought thought) {
        System.out.println(this + " thinks: \"" + thought + "\".");
    }

    public void addMemory(Memory memory) {
        this.memories.add(memory);
    }

    @Override
    public Memory nextMemory() throws NoMemoriesLeftException {
        if (this.nextMemoryIndex >= this.memories.size()) {
            throw new NoMemoriesLeftException(this + " has no memories to remember.");
        }
        return this.memories.get(this.nextMemoryIndex++);
    }

    @Override
    public int getMemoriesLeftCount() {
        return this.memories.size() - this.nextMemoryIndex;
    }

    public void rememberNextMemoryDetails() {
        try {
            Memory memory = this.nextMemory();
            System.out.println(this + " remembers: \"" + memory + "\".");
        } catch (NoMemoriesLeftException e) {
            System.out.println(this + " can't remember anything.");
        }
    }

    public void hug(HumanCapable other) {
        System.out.println(this + " hugs " + other + ".");
    }
}
