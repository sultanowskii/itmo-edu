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

    public ExecuteScriptCommand(Scanner scanner, PrintWriter printWriter) {
        super("execute_script", scanner, printWriter);
    }

    // TODO: Как-то отследить рекурсивный вызов
    @Override
    public void exec(List<String> args, Context context) throws InvalidCommandArgumentException {
        if (args.size() != 1) {
           throw new InvalidCommandArgumentException("Command syntax:\n " + this.getName() + " <script_file_name>");
        }

        CommandManager commandManager = context.getCommandManager();

        for (String filename : args) {
            try {
                scanner = new Scanner(new FileReader(filename));
            } catch (FileNotFoundException e) {
                throw new InvalidCommandArgumentException("File `" + filename + "` is inaccessible (doesn't exist, is a directory or is unreadable due to permissions.");
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                CommandInputInfo commandInputInfo = CommandParser.parseString(line);
                commandManager.execCommandByCommandInputInfo(commandInputInfo);
            }
            scanner.close();
        }
    }

    @Override
    public String getHelp() {
        return "Execute `file_name` script (command-by-command). Syntax: " + this.getName() + " file_name";
    }
}
