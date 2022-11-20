package ru.itmo.lab3;

import ru.itmo.lab3.mental.CryLevel;
import ru.itmo.lab3.mental.Crying;
import ru.itmo.lab3.mental.Memory;
import ru.itmo.lab3.mental.Remembering;
import ru.itmo.lab3.movement.Movement;

import java.util.ArrayList;
import java.util.List;

public class Human extends Entity implements Crying, Remembering {
    private CryLevel cryLevel;
    private final List<Memory> memories;
    private int nextMemoryIndex;

    public Human(String name, CryLevel cryLevel) {
        super(name);
        this.cryLevel = cryLevel;
        this.memories = new ArrayList<>();
        this.nextMemoryIndex = 0;
    }

    public Human(String name) {
        this(name, CryLevel.NOT_CRYING);
    }

    @Override
    public void cry() {
        System.out.println(this + " cries (" + this.cryLevel + ").");
    }

    @Override
    public void decreaseCryLevel() {
        CryLevel prevCryLevel = cryLevel.prev();
        if (prevCryLevel == this.cryLevel) return;

        System.out.println(this + "'s cry level dropped down to " + prevCryLevel);
        this.cryLevel = prevCryLevel;
    }

    @Override
    public void increaseCryLevel() {
        CryLevel nextCryLevel = cryLevel.next();
        if (nextCryLevel == this.cryLevel) return;


        System.out.println(this + "'s cry level rose up to " + nextCryLevel);
        this.cryLevel = nextCryLevel;
    }

    public void moveTo(Movement movement, Location destination) {
        System.out.print(this + " ");
        movement.moveTo(destination);
    }

    public void addMemory(Memory memory) {
        this.memories.add(memory);
    }

    @Override
    public Memory nextMemory() {
        if (this.nextMemoryIndex >= this.memories.size()) {
            return new Memory("");
        }
        return this.memories.get(this.nextMemoryIndex++);
    }

    @Override
    public int getMemoriesLeftCount() {
        return this.memories.size() - this.nextMemoryIndex;
    }

    public void rememberNextMemoryDetails() {
        Memory memory = this.nextMemory();
        if (memory.getDescription().equals("")) {
            return;
        }

        System.out.println(this + " remembers: \"" + memory + "\"");
    }

    @Override
    public int hashCode() {
        return ("Human_" + this.getName()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        boolean haveSameClass = this.getClass() == obj.getClass();
        if (!haveSameClass)
            return false;

        Human otherHuman = (Human) obj;

        return this.hashCode() == otherHuman.hashCode();
    }
}
