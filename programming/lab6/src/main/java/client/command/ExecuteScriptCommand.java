package client.command;

import client.CLI;
import lib.command.Command;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.manager.CommandManager;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;
import server.runtime.Context;
import server.runtime.exceptions.MaxCallDepthException;

import java.io.*;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script");
    }

    @Override
    public boolean isClientSide() {
        return true;
    }

    @Override
    public void exec(
        PrintWriter printWriter,
        String[] args,
        Serializable additionalObject,
        Context context
    ) throws InvalidCommandArgumentException, MaxCallDepthException {
        if (args.length != 1) {
            throw new InvalidCommandArgumentException("Command syntax:\n " + this.getName() + " <script_file_name>");
        }

        // TODO: fix, for now works as temporary workaround
        CLI cli = (CLI) additionalObject;

        for (String scriptFilename : args) {
            Scanner scriptFileScanner;
            File scriptFile;
            try {
                scriptFile = new File(scriptFilename);
                scriptFileScanner = new Scanner(scriptFile);
            } catch (FileNotFoundException e) {
                throw new InvalidCommandArgumentException("File `" + scriptFilename + "` is inaccessible (doesn't exist, is a directory or is unreadable due to permissions).");
            }
//            try {
//                context.pushNestedScriptName(scriptFile.getAbsolutePath());
//            } catch (RecursiveCallException e) {
//                printWriter.println(e.getMessage());
//                continue;
//            }

            while (scriptFileScanner.hasNextLine()) {
                printWriter.println();
                String line = scriptFileScanner.nextLine();
                CommandInputInfo commandInputInfo = CommandParser.parseString(line);

                try {
                    cli.execCommand(commandInputInfo, scriptFileScanner);
                } catch (InvalidCommandArgumentException e) {
                    printWriter.println("Invalid arguments: " + e.getMessage());
                } catch (ValidationException e) {
                    printWriter.println("Validation error: " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    printWriter.println("Unexpected programming error. Details: " + e.getMessage());
                } catch (IOException | RuntimeException e) {
                    printWriter.println(e.getMessage());
                }
            }
            scriptFileScanner.close();
//            context.popNestedScriptName();
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