package client.gui.worker;


import javax.swing.*;
import java.util.List;

public class CommandExecutorWorker extends SwingWorker<Void, String> {
    private CommandQueueExecutor commandQueueExecutor;
    private boolean isRunning;

    public CommandExecutorWorker(CommandQueueExecutor commandQueueExecutor) {
        this.commandQueueExecutor = commandQueueExecutor;
        this.isRunning = true;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (isRunning) {
            Thread.sleep(300);
            commandQueueExecutor.exec();
        }
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for (String chunk : chunks) {
            JOptionPane.showMessageDialog(null, chunk);
        }
    }

    public void stop() {
        this.isRunning = false;
        this.cancel(true);
    }
}
