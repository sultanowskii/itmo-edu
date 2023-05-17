package server.runtime;

import lib.command.manager.CommandManager;
import server.manager.PersonManager;
/**
 * Context
 */
public class Context {
    private PersonManager personManager;
    private CommandManager commandManager;
    private String collectionFilename;

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
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

    public void setCollectionFilename(String collectionFilename) {
        this.collectionFilename = collectionFilename;
    }
}
