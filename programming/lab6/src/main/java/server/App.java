package server;

import client.command.ExecuteScriptCommand;
import lib.command.*;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;
import lib.network.Config;
import server.command.ExitServerCommand;
import server.file.PersonCollectionXMLReader;
import server.manager.PersonManager;
import server.network.Server;
import server.runtime.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    public static void addClientCommands(CommandManager cmdManager) {
        cmdManager.addCommand(new HelpCommand());
        cmdManager.addCommand(new InfoCommand());
        cmdManager.addCommand(new ShowCommand());
        cmdManager.addCommand(new AddCommand());
        cmdManager.addCommand(new UpdateCommand());
        cmdManager.addCommand(new RemoveByIDCommand());
        cmdManager.addCommand(new ClearCommand());
        cmdManager.addCommand(new ExecuteScriptCommand());
        cmdManager.addCommand(new AddIfMinCommand());
        cmdManager.addCommand(new RemoveGreaterCommand());
        cmdManager.addCommand(new RemoveLowerCommand());
        cmdManager.addCommand(new RemoveAllByLocationCommand());
        cmdManager.addCommand(new CountGreaterThanLocationCommand());
        cmdManager.addCommand(new PrintFieldDescendingNationalityCommand());
        cmdManager.addCommand(new DoNothingCommand());
    }

    public static void addServerCommands(CommandManager commandManager) {
        commandManager.addCommand(new SaveCommand());
        commandManager.addCommand(new ExitServerCommand());
    }

    public static void main(String[] args) throws IOException {
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

            String collectionFilename = System.getenv("COLLECTION_FILE");

            if (collectionFilename == null) {
                printWriter.println("Can't find `COLLECTION_FILE` env variable. Exiting...");
                return;
            }

            PersonCollectionXMLReader reader;

            File collectionFile = new File(collectionFilename);
            if (collectionFile.exists() && !collectionFile.canRead()) {
                printWriter.println("Can't access file: Permission denied. Exiting...");
                return;
            }

            PersonManager personManager = null;
            try (
                Scanner fileScanner = new Scanner(collectionFile);
            ) {
                reader = new PersonCollectionXMLReader(printWriter, fileScanner);

                try {
                    personManager = reader.readCollectionManager();
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    printWriter.println("Programming error. Details: " + e.getMessage() + " Exiting...");
                    return;
                } catch (NullPointerException e) {
                    printWriter.println("Collection file seems to be invalid. Exiting...");
                    return;
                } catch (ValidationException e) {
                    printWriter.println("Collection file seems to be invalid. Reason: " + e.getMessage());
                    printWriter.println("Exiting...");
                    return;
                } catch (RuntimeException e) {
                    printWriter.println(e.getMessage() + " Exiting...");
                }
            } catch (FileNotFoundException e) {
                printWriter.println("File doesn't exist. Ignoring.");
            }

            if (personManager == null) {
                personManager = new PersonManager(new LinkedHashSet<>());
                personManager.setInitDateTime(ZonedDateTime.now());
            }

            ProgramStateManager programStateManager = ProgramStateManager.getInstance();

            CommandManager clientCommandManager = new CommandManager();
            addClientCommands(clientCommandManager);

            Context context = new Context();
            context.setCommandManager(clientCommandManager);
            context.setPersonManager(personManager);
            context.setCollectionFilename(collectionFilename);

            Server server;
            try {
                server = new Server(config.hostname, config.port, printWriter, context);
            } catch (SocketException e) {
                printWriter.println("Can't run server at " + config.hostname + ":" + config.port + ". Details: " + e.getMessage());
                return;
            }

            var serverThread = new Thread(server);
            serverThread.start();

            var serverOnlyCommandManager = new CommandManager();
            addServerCommands(serverOnlyCommandManager);

            while (programStateManager.getIsRunning()) {
                printWriter.print("> ");
                printWriter.flush();

                String line = scanner.nextLine();

                CommandInputInfo commandInputInfo = CommandParser.parseString(line);

                var commandName = commandInputInfo.getCommandName();
                var arguments = commandInputInfo.getArgs();

                Command command;
                try {
                    command = serverOnlyCommandManager.getCommandByName(commandName);
                } catch (NoSuchElementException e) {
                    printWriter.println("Command not found: " + commandName);
                    continue;
                }

                command.validateArguments(arguments);
                var additionalObject = command.getAdditionalObjectFromUser(printWriter, scanner);

                try {
                    command.exec(printWriter, arguments, additionalObject, context);
                } catch (InvalidCommandArgumentException e) {
                    printWriter.println("Invalid arguments: " + e.getMessage());
                } catch (ValidationException e) {
                    printWriter.println("Validation error: " + e.getMessage());
                } catch (RuntimeException e) {
                    printWriter.println("Something unexpected occured: " + e.getMessage());
                } catch (IOException e) {
                    printWriter.println("IO error. Details: " + e.getMessage());
                }
            }

            serverThread.interrupt();
        }
    }
}
