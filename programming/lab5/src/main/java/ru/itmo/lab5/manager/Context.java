package ru.itmo.lab5.manager;

import ru.itmo.lab5.command.CommandManager;

public class Context {
    private PersonManager personManager;
    private CommandManager commandManager;

    public PersonManager getPersonManager() {
        return this.personManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
