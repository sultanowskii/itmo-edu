package client.runtime;

import client.command.CommandExecutor;
import lib.command.manager.CommandManager;
import server.runtime.exceptions.RecursiveCallException;

import java.util.EmptyStackException;
import java.util.Stack;

public class ClientContext {
    private CommandExecutor commandExecutor;

    private Stack<String> scriptNestedStack = new Stack<>();

    public ClientContext(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public CommandExecutor getCommandExecutor() {
        return this.commandExecutor;
    }

    public void pushNestedScriptName(String scriptName) throws RecursiveCallException {
        if (this.scriptNestedStack.contains(scriptName)) {
            throw new RecursiveCallException("Recursion call detected.");
        }
        this.scriptNestedStack.push(scriptName);
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public String popNestedScriptName() throws EmptyStackException {
        return this.scriptNestedStack.pop();
    }
}