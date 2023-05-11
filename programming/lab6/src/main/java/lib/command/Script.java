package lib.command;

import lib.command.parse.CommandInputInfo;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class Script {
    private String filename;
    private ArrayDeque<CommandInputInfo> commands;

    public Script(String filename) {
        this.filename = filename;
        this.commands = new ArrayDeque<>();
    }

    public Script(String filename, ArrayDeque<CommandInputInfo> commands) {
        this.filename = filename;
        this.commands = commands;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Queue<CommandInputInfo> getCommands() {
        return commands;
    }

    public void setCommands(ArrayDeque<CommandInputInfo> commands) {
        this.commands = commands;
    }

    public void pushCommandInputInfo(CommandInputInfo commandInputInfo) {
        this.commands.add(commandInputInfo);
    }

    public CommandInputInfo popCommandInputInfo() {
        var result = this.commands.getFirst();
        this.commands.removeFirst();
        return result;
    }
}
