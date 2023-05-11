package client.command;

import lib.command.Command;
import lib.command.Script;
import lib.command.exception.InvalidCommandArgumentException;
import lib.command.parse.CommandInputInfo;
import lib.command.parse.CommandParser;
import server.runtime.Context;
import server.runtime.RuntimeContants;
import server.runtime.exceptions.MaxCallDepthException;
import server.runtime.exceptions.RecursiveCallException;

import java.io.*;
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

//        if (context.getNestedScriptCount() >= RuntimeContants.MAX_CALL_DEPTH) {
//            throw new MaxCallDepthException("Call depth excceded. Ignoring.");
//        }

        for (String scriptFilename : args) {
            Scanner scriptFileScanner;
            File scriptFile;
            try {
                scriptFile = new File(scriptFilename);
                scriptFileScanner = new Scanner(scriptFile);
            } catch (FileNotFoundException e) {
                throw new InvalidCommandArgumentException("File `" + scriptFilename + "` is inaccessible (doesn't exist, is a directory or is unreadable due to permissions).");
            }

            Script script = new Script(scriptFile.getAbsolutePath());

            while (scriptFileScanner.hasNextLine()) {
                String line = scriptFileScanner.nextLine();
                CommandInputInfo commandInputInfo = CommandParser.parseString(line);
                script.pushCommandInputInfo(commandInputInfo);
            }

            scriptFileScanner.close();

//            try {
//                context.pushNestedScript(script);
//            } catch (RecursiveCallException e) {
//                printWriter.println(e.getMessage());
//            }
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
