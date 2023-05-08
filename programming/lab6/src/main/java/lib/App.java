package lib;

import lib.command.exception.InvalidCommandArgumentException;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;

import java.io.*;

public class App
{

    public static void main( String[] args ) {
        while (programStateManager.getIsRunning()) {
            printWriter.print("> ");
            printWriter.flush();

            String line = scanner.nextLine();

            CommandInputInfo commandInputInfo = CommandParser.parseString(line);

            try {
                cmdManager.execCommandByCommandInputInfo(printWriter, commandInputInfo, ..., context);
            } catch (InvalidCommandArgumentException e) {
                printWriter.println("Invalid arguments: " + e.getMessage());
            } catch (ValidationException e) {
                printWriter.println("Validation error: " + e.getMessage());
            } catch (IOException | RuntimeException e) {
                printWriter.println(e.getMessage());
            }
        }
    }
}
