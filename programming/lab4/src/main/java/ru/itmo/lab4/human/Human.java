package ru.itmo.lab4.human;

import ru.itmo.lab4.mental.CryLevel;

public class Human extends HumanCapable {
    public Human(String name, CryLevel cryLevel) {
        super(name, cryLevel);
    }

    public Human(String name) {
        super(name);
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
