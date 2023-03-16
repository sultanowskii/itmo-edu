package ru.itmo.lab5.command;

import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.command.parse.CommandParser;
import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.runtime.Context;
import ru.itmo.lab5.runtime.RuntimeContants;
import ru.itmo.lab5.runtime.exception.MaxCallDepthException;
import ru.itmo.lab5.runtime.exception.RecursiveCallException;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script");
    }

    @Override
    public void exec(
            Scanner scanner,
            PrintWriter printWriter,
            List<String> args,
            Context context
    ) throws InvalidCommandArgumentException, MaxCallDepthException {
        if (args.size() != 1) {
            throw new InvalidCommandArgumentException("Command syntax:\n " + this.getName() + " <script_file_name>");
        }

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
                try {
                    nestedCommandManager.execCommandByCommandInputInfo(scriptFileScanner, printWriter, commandInputInfo, context);
                } catch (InvalidCommandArgumentException e) {
                    printWriter.println("Invalid arguments: " + e.getMessage());
                } catch (ValidationException e) {
                    printWriter.println("Validation error: " + e.getMessage());
                } catch (IOException | RuntimeException e) {
                    printWriter.println(e.getMessage());
                }
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
