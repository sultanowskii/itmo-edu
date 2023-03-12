package ru.itmo.lab5;

import ru.itmo.lab5.command.*;
import ru.itmo.lab5.command.CommandManager;
import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.command.parse.CommandParser;
import ru.itmo.lab5.file.PersonCollectionXMLReader;
import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.manager.ProgramStateManager;
import ru.itmo.lab5.schema.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

public class App
{
    public static void main( String[] args )
    {
        String containerFileName = System.getenv("COLLECTION_FILE");

        Scanner scanner = new Scanner(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        // TODO: Вынести чтение файла в отдельное место
        // TODO: придумать, че делать с закрытием потоков перед выходом
        PersonCollectionXMLReader reader;
        Scanner fileScanner;
        try {
            File collectionFile = new File(containerFileName);
            if (!collectionFile.canRead()) {
                printWriter.println("Can't access file: Permission denied. Exiting...");
                printWriter.close();
                return;
            }
            fileScanner = new Scanner(collectionFile);
            reader = new PersonCollectionXMLReader(scanner, printWriter, fileScanner);
        } catch (FileNotFoundException e) {
            printWriter.println("File doesn't exist. Ignoring.");
            return;
        } catch (NullPointerException e) {
            printWriter.println("Can't find `COLLECTION_FILE` env variable. Exiting...");
            printWriter.close();
            return;
        }

        PersonManager personManager;
        try {
            personManager = reader.readCollectionManager();
            fileScanner.close();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            printWriter.println("Programming error. Details: " + e.getMessage() + " Exiting...");
            printWriter.close();
            return;
        } catch (NullPointerException e) {
            printWriter.println("Collection file seems to be invalid. Exiting...");
            printWriter.close();
            return;
        } catch (ValidationException e) {
            printWriter.println("Collection file seems to be invalid. Reason: " + e.getMessage());
            printWriter.println("Exiting...");
            printWriter.close();
            return;
        } catch (RuntimeException e) {
            printWriter.println(e.getMessage() + " Exiting...");
            printWriter.close();
            return;
        }

        if (personManager == null) {
            personManager = new PersonManager(new LinkedHashSet<>());
            // TODO: выпилить
            for (int i = 0; i < 5; i++) {
                Coordinates coordinates = new Coordinates();
                coordinates.setX(1.0f + i);
                coordinates.setY(2 + i);
                Location location = new Location();
                location.setName("city #" + i);
                location.setX(3d + i);
                location.setY(4 + i);
                Person person = new Person();
                person.setCoordinates(coordinates);
                person.setCreationDate(ZonedDateTime.now());
                person.setNationality(Country.THAILAND);
                person.setName("ivan");
                person.setHeight(170);
                person.setEyeColor(Color.RED);
                person.setPassportID("aaaa");
                person.setLocation(location);
                personManager.add(person);
            }
        }

        ProgramStateManager programStateManager = ProgramStateManager.getInstance();
        Context context = new Context();

        CommandManager cmdManager = new CommandManager();
        context.setCommandManager(cmdManager);
        context.setPersonManager(personManager);

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

        while (programStateManager.getIsRunning()) {
            printWriter.print("> ");
            printWriter.flush();
            String line = scanner.nextLine();
            CommandInputInfo commandInputInfo = CommandParser.parseString(line);
            try {
                cmdManager.execCommandByCommandInputInfo(scanner, printWriter, commandInputInfo, context);
            } catch (NoSuchElementException e) {
                printWriter.println(e.getMessage());
            } catch (InvalidCommandArgumentException e) {
                printWriter.println("Invalid arguments: " + e.getMessage());
            } catch (ValidationException e) {
                printWriter.println("Validation error: " + e.getMessage());
            }
        }

        scanner.close();
        printWriter.close();
    }
}
