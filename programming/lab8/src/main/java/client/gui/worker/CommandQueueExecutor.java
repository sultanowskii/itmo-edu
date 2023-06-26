package client.gui.worker;

import client.command.CommandExecutor;
import client.runtime.ClientContext;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.parse.CommandExecution;
import lib.command.parse.CommandResult;
import lib.form.validation.ValidationException;

import javax.swing.*;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandQueueExecutor {
    private Queue<CommandExecution> commandExecutionQueue;
    private CommandExecutor commandExecutor;
    private ClientContext context;

    public CommandQueueExecutor(CommandExecutor commandExecutor, ClientContext context) {
        this.commandExecutor = commandExecutor;
        this.context = context;
        this.commandExecutionQueue = new ConcurrentLinkedQueue<>();
    }

    public void exec() {
        var messageBundle = context.getLocalizationManager().getMessageBundle();

        var commandExecution = commandExecutionQueue.poll();

        if (commandExecution == null) {
            return;
        }

        var commandInputInfo = commandExecution.getCommandInputInfo();
        CommandResult result;

        try {
            result = this.commandExecutor.execCommandWithAllArgumentsProvided(commandInputInfo, messageBundle);
        } catch (InvalidCommandArgumentException e) {
            result = new CommandResult(messageBundle.getString("error.invalidArgument") + ": " + e.getMessage(), false);
        } catch (ValidationException e) {
            result = new CommandResult(messageBundle.getString("error.validation") + ": " + e.getMessage(), false);
        } catch (NoSuchElementException e) {
            result = new CommandResult(messageBundle.getString("error.cmdNotFound") + ": " + commandInputInfo.getCommandName(), false);
        } catch (IOException e) {
            result = new CommandResult(messageBundle.getString("error.io") + ": " + e.getMessage(), false);
        } catch (ClassNotFoundException e) {
            result = new CommandResult(messageBundle.getString("error.programming") + ": " + e.getMessage(), false);
        } catch (RuntimeException e) {
            result = new CommandResult(messageBundle.getString("error.unexpected") + ": " + e.getMessage(), false);
        }

        commandExecution.setResult(result);
    }

    public void addCommandExecutionToQueue(CommandExecution commandExecution) {
        var result = this.commandExecutionQueue.add(commandExecution);
    }
}
