package lib.command.parse;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CommandExecution implements Serializable {
    private final CommandInputInfo commandInputInfo;
    private CommandResult result = null;
    private List<Consumer<CommandResult>> listeners;

    public CommandExecution(CommandInputInfo commandInputInfo) {
        this.listeners = new CopyOnWriteArrayList<>();
        this.commandInputInfo = commandInputInfo;
    }

    public CommandInputInfo getCommandInputInfo() {
        return this.commandInputInfo;
    }

    public CommandResult getResult() {
        return this.result;
    }

    public void setResult(CommandResult result) {
        this.result = result;
        this.notifyListeners(this.result);
    }

    public void addLitstener(Consumer<CommandResult> listener) {
        this.listeners.add(listener);
    }

    public void notifyListeners(CommandResult result) {
        for (var listener : listeners) {
            listener.accept(result);
        }
    }
}
