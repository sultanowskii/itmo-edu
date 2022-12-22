package ru.itmo.lab4.human;

import ru.itmo.lab4.mental.CryLevel;

import java.util.HashSet;
import java.util.Set;

public class HumanGroup extends HumanCapable {
    static class GroupMembersStringGenerator {
        public static String generateGroupHumansString(Set<Human> humans) {
            StringBuilder result = new StringBuilder("(");
            for (Human human : humans) {
                result.append(human.getName());
                result.append(",");
            }
            result.append(")");
            return result.toString();
        }
    }
    private final Set<Human> humans;

    public HumanGroup(String name, CryLevel cryLevel) {
        super(name, cryLevel);
        humans = new HashSet<>();
    }

    public HumanGroup(String name) {
        super(name);
        humans = new HashSet<>();
    }

    public void addHuman(Human human) {
        humans.add(human);
    }

    public void removeHuman(Human human) {
        humans.remove(human);
    }

    @Override
    public String getName() {
        String groupName = super.getName();
        if (!groupName.isEmpty()) {
            groupName = groupName.concat(" ");
        }
        StringBuilder name = new StringBuilder("Group " + groupName);
        String groupMembers = GroupMembersStringGenerator.generateGroupHumansString(this.humans);
        name.append(groupMembers);
        return name.toString();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int hashCode() {
        return ("HumanGroup_" + this.getName()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        boolean haveSameClass = this.getClass() == obj.getClass();
        if (!haveSameClass)
            return false;

        HumanGroup otherHumanGroup = (HumanGroup) obj;

        if (!this.humans.equals(otherHumanGroup.humans))
            return false;

        return this.hashCode() == otherHumanGroup.hashCode();
    }
}
