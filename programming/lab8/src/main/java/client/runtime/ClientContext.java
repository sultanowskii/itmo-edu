package client.runtime;

import client.command.CommandExecutor;
import lib.auth.Credentials;
import lib.command.manager.CommandManager;
import server.manager.PersonManager;
import server.runtime.exceptions.RecursiveCallException;

import java.util.EmptyStackException;
import java.util.ResourceBundle;
import java.util.Stack;

public class ClientContext {
    private CommandExecutor commandExecutor;

    private Stack<String> scriptNestedStack = new Stack<>();
    private Credentials credentials;
    private PersonManager personManager;
    private ResourceBundle messageBundle;

    public ClientContext(CommandExecutor commandExecutor, Credentials credentials) {
        this.commandExecutor = commandExecutor;
        this.credentials = credentials;
    }

    public ClientContext(CommandExecutor commandExecutor) {
        this();
        this.commandExecutor = commandExecutor;
    }

    public ClientContext() {
        this.credentials = new Credentials("", "");
    }

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandExecutor getCommandExecutor() {
        return this.commandExecutor;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public ResourceBundle getMessageBundle() {
        return this.messageBundle;
    }

    public void pushNestedScriptName(String scriptName) throws RecursiveCallException {
        if (this.scriptNestedStack.contains(scriptName)) {
            throw new RecursiveCallException("Recursion call detected.");
        }
        this.scriptNestedStack.push(scriptName);
    }

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String popNestedScriptName() throws EmptyStackException {
        return this.scriptNestedStack.pop();
    }

    public void setMessageBundle(ResourceBundle messageBundle) {
        this.messageBundle = messageBundle;
    }
}