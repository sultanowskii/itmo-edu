package client.runtime;

import lib.command.Script;
import lib.command.manager.CommandManager;
import lib.manager.ProgramStateManager;
import server.runtime.exceptions.RecursiveCallException;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Context
 */
public class Context {
    private ProgramStateManager programStateManager;
    private CommandManager commandManager;
    private Stack<Script> scriptNestedStack = new Stack<>();

    public ProgramStateManager getProgramStateManager() {
        return this.programStateManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public void pushNestedScript(Script script) throws RecursiveCallException {
        if (this.scriptNestedStack.contains(script)) {
            throw new RecursiveCallException("Recursion call detected.");
        }
        this.scriptNestedStack.push(script);
    }

    public int getNestedScriptCount() {
        return this.scriptNestedStack.size();
    }

    public Script popNestedScript() throws EmptyStackException {
        return this.scriptNestedStack.pop();
    }

    public void setProgramStateManager(ProgramStateManager programStateManager) {
        this.programStateManager = programStateManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
