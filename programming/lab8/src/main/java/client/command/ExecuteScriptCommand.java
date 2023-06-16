package client.command;

import client.runtime.ClientContext;
import lib.command.Command;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import lib.form.validation.ValidationException;
import server.runtime.Context;
import server.runtime.exceptions.MaxCallDepthException;
import server.runtime.exceptions.RecursiveCallException;
import server.schema.User;

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
        Context context,
        User user
    ) throws InvalidCommandArgumentException, MaxCallDepthException {
        if (args.length != 1) {
            throw new InvalidCommandArgumentException("Command syntax:\n " + this.getName() + " <script_file_name>");
        }

        var commandExecutor = clientContext.getCommandExecutor();
        var messageBundle = context.getMessageBundle();

        for (String scriptFilename : args) {
            Scanner scriptFileScanner;
            File scriptFile;
            try {
                scriptFile = new File(scriptFilename);
                scriptFileScanner = new Scanner(scriptFile);
            } catch (FileNotFoundException e) {
                throw new InvalidCommandArgumentException(scriptFilename + ": " + messageBundle.getString("error.fileNotFound"));
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
                    commandExecutor.execCommand(commandInputInfo, scriptFileScanner, messageBundle);
                } catch (InvalidCommandArgumentException e) {
                    printWriter.println(messageBundle.getString("error.invalidArgument") + ": " + e.getMessage());
                } catch (ValidationException e) {
                    printWriter.println(messageBundle.getString("error.validation") + ": " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    printWriter.println(messageBundle.getString("error.unexpected") + ": " + e.getMessage());
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