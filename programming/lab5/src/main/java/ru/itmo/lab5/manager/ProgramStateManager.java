package ru.itmo.lab5.manager;

/**
 * Program state manager
 */
public class ProgramStateManager {
    private static ProgramStateManager instance;
    private boolean isRunning = true;

    /**
     * Internal constructor
     */
    private ProgramStateManager() {

    }

    /**
     * Get instance (creates for the first time)
     * @return Instance
     */
    public static ProgramStateManager getInstance() {
        if (instance == null)
            instance = new ProgramStateManager();
        return instance;
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
