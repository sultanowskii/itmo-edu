package client;

import client.runtime.Context;
import lib.command.ExitCommand;
import client.network.Client;
import lib.command.*;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App
{
    public static void addCommands(CommandManager cmdManager) {
        cmdManager.addCommand(new HelpCommand());
        cmdManager.addCommand(new InfoCommand());
        cmdManager.addCommand(new ShowCommand());
        cmdManager.addCommand(new AddCommand());
        cmdManager.addCommand(new UpdateCommand());
        cmdManager.addCommand(new RemoveByIDCommand());
        cmdManager.addCommand(new ClearCommand());
        cmdManager.addCommand(new ExecuteScriptCommand());
        cmdManager.addCommand(new ExitCommand());
        cmdManager.addCommand(new AddIfMinCommand());
        cmdManager.addCommand(new RemoveGreaterCommand());
        cmdManager.addCommand(new RemoveLowerCommand());
        cmdManager.addCommand(new RemoveAllByLocationCommand());
        cmdManager.addCommand(new CountGreaterThanLocationCommand());
        cmdManager.addCommand(new PrintFieldDescendingNationalityCommand());
        cmdManager.addCommand(new DoNothingCommand());
    }

    public void executeCommand() {

    }

    public static void main( String[] args ) {
        try (
            Scanner scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(System.out);
        ) {
            Client client;
            try {
                client = new Client("127.0.0.1", 9999);
            } catch (UnknownHostException e) {
                // TODO: ОБРАБОТАТЬ
                System.out.println(e.getMessage());
                return;
            } catch (SocketException e) {
                // TODO: ОБРАБОТАТЬ
                System.out.println(e.getMessage());
                return;
            }

            ProgramStateManager programStateManager = ProgramStateManager.getInstance();

            CommandManager cmdManager = new CommandManager();

            addCommands(cmdManager);


            CLI cli = new CLI(scanner, printWriter, client, cmdManager, programStateManager);
            cli.loop();
        } catch (java.util.NoSuchElementException ignored) {}
    }
}