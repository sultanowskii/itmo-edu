package client.gui.worker;

import lib.command.parse.CommandExecution;
import lib.command.parse.CommandInputInfo;

import javax.swing.*;

public class CollectionUpdateWorker extends SwingWorker<Void, Void> {
    private final static long TIMEOUT = 5000;
    private final CommandQueueExecutor commandQueueExecutor;

    public CollectionUpdateWorker(CommandQueueExecutor commandQueueExecutor) {
        this.commandQueueExecutor = commandQueueExecutor;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!isCancelled()) {
            var commandExecution = new CommandExecution(
                new CommandInputInfo("raw_collection")
            );
            this.commandQueueExecutor.addCommandExecutionToQueue(commandExecution);
            Thread.sleep(TIMEOUT);
        }
        return null;
    }
}
