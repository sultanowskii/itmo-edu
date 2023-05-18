package server.runtime;

import lib.command.manager.CommandManager;
import server.db.Database;
import server.manager.PersonManager;
/**
 * Context
 */
public class Context {
    private PersonManager personManager;
    private CommandManager commandManager;
    private Database db;

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public Database getDB() {
        return db;
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
}
