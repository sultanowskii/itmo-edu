package client;

import client.network.Client;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
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

    public void loop() {
        while (programStateManager.getIsRunning()) {
            printWriter.print("> ");
            printWriter.flush();

            String line = scanner.nextLine();

            CommandInputInfo commandInputInfo = CommandParser.parseString(line);

            // TODO: ОБРАБОТАТЬ
            try {
                this.execCommand(commandInputInfo);
            } catch (InvalidCommandArgumentException e) {
                printWriter.println("Invalid arguments: " + e.getMessage());
            } catch (ValidationException e) {
                printWriter.println("Validation error: " + e.getMessage());
            } catch (RuntimeException e) {
                printWriter.println(e.getMessage());
            } catch (IOException e) {
                printWriter.println(e.getMessage());
            }
        }
    }

    public void execCommand(
        CommandInputInfo commandInputInfo
    ) throws NoSuchElementException, InvalidCommandArgumentException, IOException {
        var commandName = commandInputInfo.getCommandName();
        var arguments = commandInputInfo.getArgs();

        var command = cmdManager.getCommandByName(commandName);

        command.validateArguments(arguments);
        var additionalObject = command.getAdditionalObjectFromUser(printWriter, scanner);

        if (command.isClientSide()) {
            command.exec(this.printWriter, arguments, additionalObject, null);
        } else {
            client.sendRequest(commandName, arguments, additionalObject);
            var result = client.getResponse();

            printWriter.println(result);
        }
    }
}
