package ru.itmo.lab5.command;

import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.command.parse.CommandInputInfo;
import ru.itmo.lab5.command.parse.CommandParser;
import ru.itmo.lab5.manager.Context;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script");
    }

    // TODO: Как-то отследить рекурсивный вызов
    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) throws InvalidCommandArgumentException {
        if (args.size() != 1) {
           throw new InvalidCommandArgumentException("Command syntax:\n " + this.getName() + " <script_file_name>");
        }

        Iterable<Command> commands = context.getCommandManager().getCommands();
        CommandManager commandManager = new CommandManager(commands);

        for (String scriptFilename : args) {
            Scanner scriptFileScanner;
            try {
                scriptFileScanner = new Scanner(new FileReader(scriptFilename));
            } catch (FileNotFoundException e) {
                throw new InvalidCommandArgumentException("File `" + scriptFilename + "` is inaccessible (doesn't exist, is a directory or is unreadable due to permissions.");
            }

            while (scriptFileScanner.hasNextLine()) {
                String line = scriptFileScanner.nextLine();
                CommandInputInfo commandInputInfo = CommandParser.parseString(line);
                commandManager.execCommandByCommandInputInfo(scriptFileScanner, printWriter, commandInputInfo, context);
                printWriter.println();
            }
            scriptFileScanner.close();
        }
    }

    @Override
    public String getHelp() {
        return "Execute `file_name` script (command-by-command). Syntax: " + this.getName() + " file_name";
    }
}
