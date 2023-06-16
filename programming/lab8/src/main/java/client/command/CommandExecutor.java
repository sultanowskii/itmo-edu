package client.command;

import client.network.Client;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.CommandInputInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CommandExecutor {
    CommandManager cmdManager;
    Client client;
    PrintWriter printWriter;

    public CommandExecutor(Client client, CommandManager cmdManager, PrintWriter printWriter) {
        this.client = client;
        this.cmdManager = cmdManager;
        this.printWriter = printWriter;
    }

    public void execCommand(
        CommandInputInfo commandInputInfo,
        Scanner scanner,
        ResourceBundle messageBundle
    ) throws NoSuchElementException, InvalidCommandArgumentException, IOException, ClassNotFoundException {
        var commandName = commandInputInfo.getCommandName();
        var arguments = commandInputInfo.getArgs();

        var command = cmdManager.getCommandByName(commandName, messageBundle);

        command.validateArguments(arguments);

        Serializable additionalObject = command.getAdditionalObjectFromUser(printWriter, scanner, messageBundle);

        if (command.isClientSide()) {
            command.exec(this.printWriter, arguments, additionalObject, null, null);
        } else {
            client.sendRequest(commandName, arguments, additionalObject);
            var result = client.getResponse();

            printWriter.print(result);
        }
    }
}
