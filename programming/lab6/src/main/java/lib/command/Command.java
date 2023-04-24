package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.form.validation.ValidationException;
import server.runtime.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Entity that performs an action based on arguments.
 */
public abstract class Command {
    protected String name;

    /**
     * Default command constructor
     * @param name Command name (used for 'calling')
     */
    public Command(String name) {
        this.name = name;
    }

    /**
     * Validate command arguments
     * @param args List of command arguments
     * @throws InvalidCommandArgumentException If arguments are invalid
     */
    public void validateArguments(List<String> args) throws InvalidCommandArgumentException {

    }

    /**
     * Perform action
     * @param scanner Global input (to get user input)
     * @param printWriter Global output (to print something to user)
     * @param args List of command arguments
     * @param context Current context
     * @throws InvalidCommandArgumentException If arguments are invalid or cause some errors
     * @throws ValidationException If user's input is invalid
     * @throws IOException On internal IO errors (usually related to files)
     */
    public abstract void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) throws InvalidCommandArgumentException, ValidationException, IOException;

    public String getName() {
        return this.name;
    }

    public abstract String getDescription();

    public abstract String getSyntax();

    public String getHelp() {
        return this.getDescription() + " Syntax: " + this.getSyntax();
    }
}
