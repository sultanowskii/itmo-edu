package client;

import client.command.ExecuteScriptCommand;
import lib.network.Config;
import lib.command.ExitCommand;
import client.network.Client;
import lib.command.*;
import lib.command.manager.CommandManager;
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
        cmdManager.addCommand(new ExecuteScriptCommand());
        cmdManager.addCommand(new ClearCommand());
        cmdManager.addCommand(new ExitCommand());
        cmdManager.addCommand(new AddIfMinCommand());
        cmdManager.addCommand(new RemoveGreaterCommand());
        cmdManager.addCommand(new RemoveLowerCommand());
        cmdManager.addCommand(new RemoveAllByLocationCommand());
        cmdManager.addCommand(new CountGreaterThanLocationCommand());
        cmdManager.addCommand(new PrintFieldDescendingNationalityCommand());
        cmdManager.addCommand(new DoNothingCommand());
    }

    public static void main( String[] args ) {
        try (
            Scanner scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(System.out);
        ) {
            String configFilename = System.getenv("LAB6_CONFIG_FILENAME");

            if (configFilename == null) {
                configFilename = "config.xml";
            }

            Config config = Config.readFromfile(configFilename, printWriter);

            if (config == null) {
                return;
            }

            Client client;

            System.out.println("Specified address: " + config.hostname + ":" + config.port);

            try {
                client = new Client(config.hostname, config.port);
            } catch (UnknownHostException | SocketException e) {
                System.out.println("Can't connect to the server. Try again later or recheck address is correct. Details: " + e.getMessage());
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