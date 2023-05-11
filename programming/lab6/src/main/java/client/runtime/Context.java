package client.runtime;

import lib.command.manager.CommandManager;
import server.runtime.exceptions.RecursiveCallException;

import java.util.EmptyStackException;
import java.util.Stack;

public class Context {
    private CommandManager commandManager;

    private Stack<String> scriptNestedStack = new Stack<>();


    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public void pushNestedScriptName(String scriptName) throws RecursiveCallException {
        if (this.scriptNestedStack.contains(scriptName)) {
            throw new RecursiveCallException("Recursion call detected.");
        }
        this.scriptNestedStack.push(scriptName);
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public String popNestedScriptName() throws EmptyStackException {
        return this.scriptNestedStack.pop();
    }
}