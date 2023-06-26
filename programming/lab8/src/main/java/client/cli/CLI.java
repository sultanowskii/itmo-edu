package client.cli;

import client.command.CommandExecutor;
import client.runtime.ClientContext;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.parse.*;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI {
    Scanner scanner;
    PrintWriter printWriter;
    ProgramStateManager programStateManager;
    CommandExecutor commandExecutor;
    ClientContext context;

    public CLI(
        Scanner scanner,
        PrintWriter printWriter,
        ClientContext context
    ) {
        this.scanner = scanner;
        this.printWriter = printWriter;
        this.programStateManager = ProgramStateManager.getInstance();
        this.commandExecutor = context.getCommandExecutor();
        this.context = context;
    }

    public void loop() {
        var messageBundle = context.getLocalizationManager().getMessageBundle();
        while (programStateManager.getIsRunning()) {
            CommandInputInfo commandInputInfo;

            printWriter.print("> ");
            printWriter.flush();

            String line = scanner.nextLine();

            commandInputInfo = CommandParser.parseString(line);

            try {
                this.commandExecutor.execCommand(commandInputInfo, this.scanner, messageBundle);
            } catch (InvalidCommandArgumentException e) {
                printWriter.println(messageBundle.getString("error.invalidArgument") + ": " + e.getMessage());
            } catch (ValidationException e) {
                printWriter.println(messageBundle.getString("error.validation") + ": " + e.getMessage());
            } catch (NoSuchElementException e) {
                printWriter.println(messageBundle.getString("error.cmdNotFound") + ": " + commandInputInfo.getCommandName());
            } catch (IOException e) {
                printWriter.println(messageBundle.getString("error.io") + ": " + e.getMessage());
            } catch (ClassNotFoundException e) {
                printWriter.println(messageBundle.getString("error.programming") + ": " + e.getMessage());
            } catch (RuntimeException e) {
                printWriter.println(messageBundle.getString("error.unexpected") + ": " + e.getMessage());
            }
        }
    }
}
