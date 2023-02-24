package ru.itmo.lab5.manager;

public class ProgramStateManager {
    private static ProgramStateManager instance;
    private boolean isRunning = true;

    private ProgramStateManager() {

    }

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
