package server;

import lib.command.*;
import lib.command.manager.CommandManager;
import lib.form.validation.ValidationException;
import lib.manager.ProgramStateManager;
import server.file.PersonCollectionXMLReader;
import server.manager.PersonManager;
import server.network.Server;
import server.runtime.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class App {
    public static void addCommands(CommandManager cmdManager) {
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (
            PrintWriter printWriter = new PrintWriter(System.out);
        ) {
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
                    return;
                }
            } catch (FileNotFoundException e) {
                printWriter.println("File doesn't exist. Ignoring.");
            }

            if (personManager == null) {
                personManager = new PersonManager(new LinkedHashSet<>());
                personManager.setInitDateTime(ZonedDateTime.now());
            }

            ProgramStateManager programStateManager = ProgramStateManager.getInstance();
            Context context = new Context();

            CommandManager cmdManager = new CommandManager();
            context.setCommandManager(cmdManager);
            context.setPersonManager(personManager);
            context.setCollectionFilename(collectionFilename);

            addCommands(cmdManager);

            Server server = new Server("127.0.0.1", 9999, context);
            server.loop();
        }
    }
}
