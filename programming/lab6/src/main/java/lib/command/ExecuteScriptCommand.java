package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;
import server.runtime.Context;
import server.runtime.exceptions.MaxCallDepthException;
import server.runtime.exceptions.RecursiveCallException;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script");
    }

    @Override
    public void validateArguments(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1) {
            throw new InvalidCommandArgumentException("Command syntax:\n " + this.getName() + " <script_file_name>");
        }
    }

    @Override
    public void exec(
        PrintWriter printWriter,
        String[] args,
        Serializable objectArgument,
        Context context
    ) throws InvalidCommandArgumentException, MaxCallDepthException {
        this.validateArguments(args);

        Iterable<Command> commands = context.getCommandManager().getCommands();
        CommandManager nestedCommandManager = new CommandManager(commands);

        for (String scriptFilename : args) {
            Scanner scriptFileScanner;
            File scriptFile;
            try {
                scriptFile = new File(scriptFilename);
                scriptFileScanner = new Scanner(scriptFile);
            } catch (FileNotFoundException e) {
                throw new InvalidCommandArgumentException("File `" + scriptFilename + "` is inaccessible (doesn't exist, is a directory or is unreadable due to permissions).");
            }
            try {
                context.pushNestedScriptName(scriptFile.getAbsolutePath());
            } catch (RecursiveCallException e) {
                printWriter.println(e.getMessage());
                continue;
            }

            while (scriptFileScanner.hasNextLine()) {
                printWriter.println();
                String line = scriptFileScanner.nextLine();
                CommandInputInfo commandInputInfo = CommandParser.parseString(line);
//                try {
//                    Command command = nestedCommandManager.getCommandByName(commandInputInfo.getCommandName());
//                    command.validateArguments(commandInputInfo.getArgs());
//                    Serializable additionalObject = command.getAdditionalObjectFromUser(printWriter, ...);
//                    nestedCommandManager.execCommandByCommandInputInfo(printWriter, commandInputInfo, additionalObject, context);
//                } catch (InvalidCommandArgumentException e) {
//                    printWriter.println("Invalid arguments: " + e.getMessage());
//                } catch (ValidationException e) {
//                    printWriter.println("Validation error: " + e.getMessage());
//                } catch (IOException | RuntimeException e) {
//                    printWriter.println(e.getMessage());
//                } catch (NoSuchElementException e) {
//                    printWriter.println("Command not found: " + commandInputInfo.getCommandName());
//                }
            }
            scriptFileScanner.close();
            context.popNestedScriptName();
        }
    }

    @Override
    public String getDescription() {
        return "Execute `file_name` script (command-by-command).";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " file_name";
    }
}
