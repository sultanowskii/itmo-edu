package client;

import client.command.CommandExecutor;
import client.network.Client;
import client.runtime.ClientContext;
import lib.command.Command;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.*;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class CLI {
    Scanner scanner;
    PrintWriter printWriter;
    ProgramStateManager programStateManager;
    CommandExecutor commandExecutor;

    public CLI(
        Scanner scanner,
        PrintWriter printWriter,
        ClientContext context
    ) {
        this.scanner = scanner;
        this.printWriter = printWriter;
        this.programStateManager = ProgramStateManager.getInstance();
        this.commandExecutor = context.getCommandExecutor();
    }

    public void loop() {
        while (programStateManager.getIsRunning()) {
            CommandInputInfo commandInputInfo;

            printWriter.print("> ");
            printWriter.flush();

            String line = scanner.nextLine();

            commandInputInfo = CommandParser.parseString(line);

            try {
                this.commandExecutor.execCommand(commandInputInfo, this.scanner);
            } catch (InvalidCommandArgumentException e) {
                printWriter.println("Invalid arguments: " + e.getMessage());
            } catch (ValidationException e) {
                printWriter.println("Validation error: " + e.getMessage());
            } catch (NoSuchElementException e) {
                printWriter.println("Command not found: " + commandInputInfo.getCommandName());
            } catch (IOException e) {
                printWriter.println("IO error. Are you connected to the server? Details: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                printWriter.println("Unexpected programming error. Details: " + e.getMessage());
            } catch (RuntimeException e) {
                printWriter.println("Something unexpected occurred: " + e.getMessage());
            }
        }
    }
}
