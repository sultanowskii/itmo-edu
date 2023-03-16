package ru.itmo.lab5;

import ru.itmo.lab5.command.*;
import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.command.parse.CommandParser;
import ru.itmo.lab5.file.PersonCollectionXMLReader;
import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.manager.ProgramStateManager;
import ru.itmo.lab5.runtime.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

    public static void main( String[] args )
    {
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
