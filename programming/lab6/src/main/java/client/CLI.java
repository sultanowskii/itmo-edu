package client;

import client.network.Client;
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

public class CLI implements Serializable {
    Scanner scanner;
    PrintWriter printWriter;
    Client client;
    CommandManager cmdManager;
    ProgramStateManager programStateManager;

    public CLI(
        Scanner scanner,
        PrintWriter printWriter,
        Client client,
        CommandManager cmdManager,
        ProgramStateManager programStateManager
    ) {
        this.scanner = scanner;
        this.printWriter = printWriter;
        this.client = client;
        this.cmdManager = cmdManager;
        this.programStateManager = programStateManager;
    }

    public CommandManager getCmdManager() {
        return this.cmdManager;
    }

    public void loop() {

        while (programStateManager.getIsRunning()) {
            CommandInputInfo commandInputInfo;

            printWriter.print("> ");
            printWriter.flush();

            String line = scanner.nextLine();

            commandInputInfo = CommandParser.parseString(line);

            try {
                this.execCommand(commandInputInfo, this.scanner);
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

    public void execCommand(
        CommandInputInfo commandInputInfo,
        Scanner scanner
    ) throws NoSuchElementException, InvalidCommandArgumentException, IOException, ClassNotFoundException {
        var commandName = commandInputInfo.getCommandName();
        var arguments = commandInputInfo.getArgs();

        var command = cmdManager.getCommandByName(commandName);

        command.validateArguments(arguments);

        Serializable additionalObject;

        if (commandName.equals("execute_script")) {
            additionalObject = this;
        } else {
            additionalObject = command.getAdditionalObjectFromUser(printWriter, scanner);
        }

        if (command.isClientSide()) {
            command.exec(this.printWriter, arguments, additionalObject, null);
        } else {
            client.sendRequest(commandName, arguments, additionalObject);
            var result = client.getResponse();

            printWriter.print(result);
        }
    }
}
