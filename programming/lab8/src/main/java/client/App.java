package client;

import client.cli.CLI;
import client.command.CommandExecutor;
import client.command.ExecuteScriptCommand;
import client.command.SigninCommand;
import client.gui.GUI;
import client.gui.worker.CommandQueueExecutor;
import client.runtime.ClientContext;
import client.runtime.Config;
import client.command.ExitClientCommand;
import client.network.Client;
import lib.command.*;
import lib.command.manager.CommandManager;
import lib.lang.LocalizationManager;
import lib.manager.ProgramStateManager;
import server.manager.PersonManager;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
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
        cmdManager.addCommand(new ExitClientCommand());
        cmdManager.addCommand(new AddIfMinCommand());
        cmdManager.addCommand(new RemoveGreaterCommand());
        cmdManager.addCommand(new RemoveLowerCommand());
        cmdManager.addCommand(new RemoveAllByLocationCommand());
        cmdManager.addCommand(new CountGreaterThanLocationCommand());
        cmdManager.addCommand(new PrintFieldDescendingNationalityCommand());
        cmdManager.addCommand(new SignupCommand());
        cmdManager.addCommand(new DoNothingCommand());
        cmdManager.addCommand(new RawCollectionCommand());
        cmdManager.addCommand(new GetUserIDCommand());
    }

    public static void main( String[] args ) {
        try (
            Scanner scanner = new Scanner(System.in);
            PrintWriter printWriter = new PrintWriter(System.out);
        ) {
            boolean guiMode = false;

            for (var arg : args) {
                if (arg.equals("--gui")) {
                    guiMode = true;
                    break;
                }
            }

            String configFilename = System.getenv("CLIENT_CONFIG");

            ClientContext clientContext = new ClientContext();

            LocalizationManager localizationManager = new LocalizationManager();
            clientContext.setLocalizationManager(localizationManager);

            if (configFilename == null) {
                configFilename = "config.xml";
            }

            Config config = Config.readFromfile(configFilename, printWriter, clientContext);

            if (config == null) {
                return;
            }

            Client client;

            try {
                client = new Client(config.hostname, config.port);
            } catch (UnknownHostException | SocketException e) {
                printWriter.println(localizationManager.getMessageBundle().getString("error.cantReachServer") + ": " + e.getMessage());
                return;
            }

            ProgramStateManager.getInstance();

            CommandManager cmdManager = new CommandManager();
            addCommands(cmdManager);

            CommandExecutor commandExecutor = new CommandExecutor(client, cmdManager, printWriter);
            clientContext.setCommandExecutor(commandExecutor);

            CommandQueueExecutor commandQueueExecutor = new CommandQueueExecutor(commandExecutor, clientContext);
            clientContext.setCommandQueueExecutor(commandQueueExecutor);

            PersonManager personManager = new PersonManager();
            personManager.setInitDateTime(ZonedDateTime.now());
            clientContext.setPersonManager(personManager);

            client.setClientContext(clientContext);

            cmdManager.addCommand(new ExecuteScriptCommand(clientContext));
            cmdManager.addCommand(new SigninCommand(clientContext));

            if (guiMode) {
                GUI gui = new GUI(clientContext);
            } else {
                CLI cli = new CLI(scanner, printWriter, clientContext);
                cli.loop();
            }
        } catch (java.util.NoSuchElementException ignored) {}
    }
}