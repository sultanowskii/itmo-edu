package ru.itmo.lab5.runtime;

import ru.itmo.lab5.command.CommandManager;
import ru.itmo.lab5.manager.PersonManager;

/**
 * Context
 */
public class Context {
    private PersonManager personManager;
    private CommandManager commandManager;
    private int depth = 0;
    private String collectionFilename;

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public int getDepth() {
        return this.depth;
    }

    public String getCollectionFilename() {
        return this.collectionFilename;
    }

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setCollectionFilename(String collectionFilename) {
        this.collectionFilename = collectionFilename;
    }
}
