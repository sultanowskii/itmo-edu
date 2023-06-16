package server;

import lib.command.*;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;
import lib.schema.Person;
import server.command.ExitServerCommand;
import server.db.Database;
import server.manager.PersonManager;
import server.network.Server;
import server.runtime.Config;
import server.runtime.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;

public class App {
    public static void addClientCommands(CommandManager cmdManager) {
        cmdManager.addCommand(new HelpCommand());
        cmdManager.addCommand(new InfoCommand());
        cmdManager.addCommand(new ShowCommand());
        cmdManager.addCommand(new AddCommand());
        cmdManager.addCommand(new UpdateCommand());
        cmdManager.addCommand(new RemoveByIDCommand());
        cmdManager.addCommand(new ClearCommand());
        cmdManager.addCommand(new AddIfMinCommand());
        cmdManager.addCommand(new RemoveGreaterCommand());
        cmdManager.addCommand(new RemoveLowerCommand());
        cmdManager.addCommand(new RemoveAllByLocationCommand());
        cmdManager.addCommand(new CountGreaterThanLocationCommand());
        cmdManager.addCommand(new PrintFieldDescendingNationalityCommand());
        cmdManager.addCommand(new DoNothingCommand());
        cmdManager.addCommand(new SignupCommand());
        cmdManager.addCommand(new RawCollectionCommand());
    }

    public static void addServerCommands(CommandManager commandManager) {
        commandManager.addCommand(new ExitServerCommand());
    }

    public static void main(String[] args) throws IOException {
        try (
            Scanner scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(System.out);
        ) {
            String configFilename = System.getenv("SERVER_CONFIG");

            if (configFilename == null) {
                configFilename = "config.xml";
            }

            Context context = new Context();

            var locale = Locale.US;
            ResourceBundle messageBundle = ResourceBundle.getBundle("MessageBundle", locale);
            context.setMessageBundle(messageBundle);

            Config config = Config.readFromfile(configFilename, printWriter, messageBundle);
            if (config == null) {
                return;
            }

            Database db = new Database(config.dbHostname, config.dbPort, config.dbName, config.dbUsername, config.dbPassword);
            context.setDB(db);

            LinkedHashSet<Person> collection;
            try {
                collection = db.getAllPersons();
            } catch (SQLException e) {
                printWriter.println(messageBundle.getString("error.db") + ": " + e.getMessage());
                return;
            }

            PersonManager personManager = new PersonManager(collection);
            personManager.setInitDateTime(ZonedDateTime.now());
            context.setPersonManager(personManager);

            ProgramStateManager programStateManager = ProgramStateManager.getInstance();

            CommandManager clientCommandManager = new CommandManager();
            addClientCommands(clientCommandManager);
            context.setCommandManager(clientCommandManager);


            Server server;
            try {
                server = new Server(config.hostname, config.port, printWriter, context);
            } catch (SocketException e) {
                printWriter.println(
                    messageBundle.getString("error.cantStartServer")
                    + ": "
                    + config.hostname + ":" + config.port
                    + ": "
                    + e.getMessage()
                );
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
                    command = serverOnlyCommandManager.getCommandByName(commandName, messageBundle);
                } catch (NoSuchElementException e) {
                    printWriter.println(messageBundle.getString("error.cmdNotFound") + ": " + commandName);
                    continue;
                }

                command.validateArguments(arguments);
                var additionalObject = command.getAdditionalObjectFromUser(printWriter, scanner, messageBundle);

                try {
                    command.exec(printWriter, arguments, additionalObject, context, null);
                } catch (InvalidCommandArgumentException e) {
                    printWriter.println(messageBundle.getString("error.cmdNotFound") + ": " + e.getMessage());
                } catch (ValidationException e) {
                    printWriter.println(messageBundle.getString("error.invalidArgument") + ": " + e.getMessage());
                } catch (RuntimeException e) {
                    printWriter.println(messageBundle.getString("error.unexpected") + ": " + e.getMessage());
                } catch (IOException e) {
                    printWriter.println(messageBundle.getString("error.io") + ": " + e.getMessage());
                }
            }

            serverThread.interrupt();
        }
    }
}
