package client.runtime;

import client.command.CommandExecutor;
import client.gui.util.GlobalRedrawer;
import client.gui.worker.CommandQueueExecutor;
import lib.auth.Credentials;
import lib.auth.CredentialsManager;
import lib.lang.LocalizationManager;
import server.manager.PersonManager;
import server.runtime.exceptions.RecursiveCallException;

import javax.swing.*;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ClientContext {
    private List<BiConsumer<PersonManager, PersonManager>> personManagerUpdateListeners;
    private CommandExecutor commandExecutor; // both

    private Stack<String> scriptNestedStack = new Stack<>(); // both
    private CredentialsManager credentialsManager; // both
    private PersonManager personManager; // both
    private LocalizationManager localizationManager; // both
    private CommandQueueExecutor commandQueueExecutor; // GUI only
    private GlobalRedrawer globalRedrawer; // GUI only
    private int userID;

    public ClientContext(CommandExecutor commandExecutor, CredentialsManager credentialsManager) {
        this.commandExecutor = commandExecutor;
        this.credentialsManager = credentialsManager;
        this.personManagerUpdateListeners = new CopyOnWriteArrayList<>();
    }

    public ClientContext(CommandExecutor commandExecutor) {
        this(commandExecutor, new CredentialsManager(new Credentials("", "")));
    }

    public ClientContext() {
        this(null);
    }

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandExecutor getCommandExecutor() {
        return this.commandExecutor;
    }

    public CredentialsManager getCredentialsManager() {
        return this.credentialsManager;
    }

    public LocalizationManager getLocalizationManager() {
        return this.localizationManager;
    }

    public CommandQueueExecutor getCommandQueueExecutor() {
        return this.commandQueueExecutor;
    }

    public int getUserID() {
        return this.userID;
    }

    public void pushNestedScriptName(String scriptName) throws RecursiveCallException {
        if (this.scriptNestedStack.contains(scriptName)) {
            throw new RecursiveCallException("Recursion call detected.");
        }
        this.scriptNestedStack.push(scriptName);
    }

    public void setPersonManager(PersonManager personManager) {
        this.notifyPersonManagerUpdateListeners(this.personManager, personManager);
        this.personManager = personManager;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public void setCredentialsManager(CredentialsManager credentialsManager) {
        this.credentialsManager = credentialsManager;
    }

    public String popNestedScriptName() throws EmptyStackException {
        return this.scriptNestedStack.pop();
    }

    public void setLocalizationManager(LocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    public void setCommandQueueExecutor(CommandQueueExecutor commandQueueExecutor) {
        this.commandQueueExecutor = commandQueueExecutor;
    }

    public GlobalRedrawer getGlobalRedrawer() {
        return globalRedrawer;
    }

    public void setGlobalRedrawer(GlobalRedrawer globalRedrawer) {
        this.globalRedrawer = globalRedrawer;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void addPersonManagerUpdateListener(BiConsumer<PersonManager, PersonManager> listener) {
        this.personManagerUpdateListeners.add(listener);
    }

    public void notifyPersonManagerUpdateListeners(PersonManager oldPersonManager, PersonManager newPersonManager) {
        for (var listener : personManagerUpdateListeners) {
            listener.accept(oldPersonManager, newPersonManager);
        }
    }
}