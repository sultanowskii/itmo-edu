package lib;

import client.command.ExitCommand;
import lib.command.*;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import server.file.PersonCollectionXMLReader;
import lib.form.validation.ValidationException;
import server.manager.PersonManager;
import lib.manager.ProgramStateManager;
import server.runtime.Context;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
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
        cmdManager.addCommand(new SaveCommand());
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

    public static void main( String[] args ) {
        try (
            Scanner scanner = new Scanner(System.in);
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
                reader = new PersonCollectionXMLReader(scanner, printWriter, fileScanner);

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

            while (programStateManager.getIsRunning()) {
                printWriter.print("> ");
                printWriter.flush();

                String line = scanner.nextLine();

                CommandInputInfo commandInputInfo = CommandParser.parseString(line);

                try {
                    cmdManager.execCommandByCommandInputInfo(scanner, printWriter, commandInputInfo, context);
                } catch (InvalidCommandArgumentException e) {
                    printWriter.println("Invalid arguments: " + e.getMessage());
                } catch (ValidationException e) {
                    printWriter.println("Validation error: " + e.getMessage());
                } catch (IOException | RuntimeException e) {
                    printWriter.println(e.getMessage());
                }
            }
        } catch (java.util.NoSuchElementException ignored) {}
    }
}
