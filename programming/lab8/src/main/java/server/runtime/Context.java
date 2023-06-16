package server.runtime;

import lib.command.manager.CommandManager;
import server.db.Database;
import server.manager.PersonManager;

import java.util.ResourceBundle;

/**
 * Context
 */
public class Context {
    private PersonManager personManager;
    private CommandManager commandManager;
    private Database db;
    private ResourceBundle messageBundle;

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public Database getDB() {
        return db;
    }

    public ResourceBundle getMessageBundle() {
        return this.messageBundle;
    }

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setDB(Database db) {
        this.db = db;
    }

    public void setMessageBundle(ResourceBundle messageBundle) {
        this.messageBundle = messageBundle;
    }
}
