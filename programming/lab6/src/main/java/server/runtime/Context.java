package server.runtime;

import lib.command.manager.CommandManager;
import server.manager.PersonManager;
import server.runtime.exceptions.RecursiveCallException;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Context
 */
public class Context {
    private PersonManager personManager;
    private CommandManager commandManager;

    private Stack<String> scriptNestedStack = new Stack<>();
    private String collectionFilename;

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public void pushNestedScriptName(String scriptName) throws RecursiveCallException {
        if (this.scriptNestedStack.contains(scriptName)) {
            throw new RecursiveCallException("Recursion call detected.");
        }
        this.scriptNestedStack.push(scriptName);
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

    public String popNestedScriptName() throws EmptyStackException {
        return this.scriptNestedStack.pop();
    }

    public void setCollectionFilename(String collectionFilename) {
        this.collectionFilename = collectionFilename;
    }
}
