package ru.itmo.lab5;

import ru.itmo.lab5.command.*;
import ru.itmo.lab5.command.CommandManager;
import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.command.parse.CommandParser;
import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.manager.PersonManager;
import ru.itmo.lab5.manager.ProgramStateManager;

import java.io.*;
import java.util.*;

public class App
{
    public static void main( String[] args )
    {
        // TODO: Переименовать
        // TODO: Проверить на то, что название файла существует
        String dataContainerFileName = System.getenv("DATA_CONTAINER_FILE");

        Scanner scanner = new Scanner(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        ProgramStateManager programStateManager = ProgramStateManager.getInstance();

        // TODO: Сперва открыть и прочитать файл
        PersonManager personStorage = new PersonManager(new LinkedHashSet<>());

        Context context = new Context();

        CommandManager cmdManager = new CommandManager(context);
        context.setCommandManager(cmdManager);
        context.setPersonManager(personStorage);

        cmdManager.addCommand(new HelpCommand(scanner, printWriter));
        cmdManager.addCommand(new InfoCommand(scanner, printWriter));
        cmdManager.addCommand(new ShowCommand(scanner, printWriter));
        cmdManager.addCommand(new AddCommand(scanner, printWriter));
        cmdManager.addCommand(new UpdateCommand(scanner, printWriter));
        cmdManager.addCommand(new RemoveByIDCommand(scanner, printWriter));
        cmdManager.addCommand(new ClearCommand(scanner, printWriter));
        cmdManager.addCommand(new ExecuteScriptCommand(scanner, printWriter));
        cmdManager.addCommand(new ExitCommand(scanner, printWriter));

        while (programStateManager.getIsRunning()) {
            printWriter.print("> ");
            printWriter.flush();
            String line = scanner.nextLine();
            CommandInputInfo commandInputInfo = CommandParser.parseString(line);
            try {
                cmdManager.execCommandByCommandInputInfo(commandInputInfo);
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
