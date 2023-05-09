package client.runtime;

import lib.command.manager.CommandManager;
import lib.manager.ProgramStateManager;

/**
 * Context
 */
public class Context {
    private ProgramStateManager programStateManager;
    private CommandManager commandManager;

    public ProgramStateManager getProgramStateManager() {
        return this.programStateManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public void setProgramStateManager(ProgramStateManager programStateManager) {
        this.programStateManager = programStateManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
