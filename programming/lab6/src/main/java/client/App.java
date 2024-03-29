package client;

import client.command.CommandExecutor;
import client.command.ExecuteScriptCommand;
import client.runtime.ClientContext;
import lib.network.Config;
import client.command.ExitClientCommand;
import client.network.Client;
import lib.command.*;
import lib.command.manager.CommandManager;
import lib.manager.ProgramStateManager;
import lib.schema.Person;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        cmdManager.addCommand(new ExitClientCommand());
        cmdManager.addCommand(new AddIfMinCommand());
        cmdManager.addCommand(new RemoveGreaterCommand());
        cmdManager.addCommand(new RemoveLowerCommand());
        cmdManager.addCommand(new RemoveAllByLocationCommand());
        cmdManager.addCommand(new CountGreaterThanLocationCommand());
        cmdManager.addCommand(new PrintFieldDescendingNationalityCommand());
        cmdManager.addCommand(new DoNothingCommand());
    }

    public List<String> additonal_task(List<List<Person>> persons) {
        return persons.stream()
            .flatMap(List::stream)
            .filter(
                person -> (
                    person.getCoordinates().getX() + person.getCoordinates().getY() > 0
                    &&
                    person.getPassportID().length() > 0
                    &&
                    Character.isDigit(person.getPassportID().charAt(0))
                )
            )
            .map(Person::getName)
            .collect(Collectors.toList());
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

            try {
                client = new Client(config.hostname, config.port);
            } catch (UnknownHostException | SocketException e) {
                printWriter.println("Can't connect to the server. Try again later or recheck address is correct. Details: " + e.getMessage());
                return;
            }

            printWriter.println("Specified address: " + config.hostname + ":" + config.port);

            ProgramStateManager.getInstance();

            CommandManager cmdManager = new CommandManager();
            addCommands(cmdManager);

            CommandExecutor commandExecutor = new CommandExecutor(client, cmdManager, printWriter);

            ClientContext clientContext = new ClientContext(commandExecutor);

            cmdManager.addCommand(new ExecuteScriptCommand(clientContext));

            CLI cli = new CLI(scanner, printWriter, clientContext);
            cli.loop();
        } catch (java.util.NoSuchElementException ignored) {}
    }
}