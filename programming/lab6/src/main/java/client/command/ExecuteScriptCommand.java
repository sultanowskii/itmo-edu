package client.command;

import client.CLI;
import client.runtime.ClientContext;
import lib.command.Command;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;
import server.runtime.Context;
import server.runtime.exceptions.MaxCallDepthException;
import server.runtime.exceptions.RecursiveCallException;

import java.io.*;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    private ClientContext clientContext;

    public ExecuteScriptCommand(ClientContext clientContext) {
        super("execute_script");
        this.clientContext = clientContext;
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

        var commandExecutor = clientContext.getCommandExecutor();

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
                this.clientContext.pushNestedScriptName(scriptFile.getAbsolutePath());
            } catch (RecursiveCallException e) {
                printWriter.println(e.getMessage());
                continue;
            }

            while (scriptFileScanner.hasNextLine()) {
                printWriter.println();
                String line = scriptFileScanner.nextLine();
                CommandInputInfo commandInputInfo = CommandParser.parseString(line);

                try {
                    commandExecutor.execCommand(commandInputInfo, scriptFileScanner);
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
            this.clientContext.popNestedScriptName();
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