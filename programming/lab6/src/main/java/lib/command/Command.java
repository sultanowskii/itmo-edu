package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.form.validation.ValidationException;
import server.runtime.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
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
    public void validateArguments(String[] args) throws InvalidCommandArgumentException {

    }

    /**
     * Determine if command executed on client side or on server's one.
     * Please note that client-side commands have to ignore `context` argument in `exec()` method.
     * @return Is this command a client-side or not
     */
    public boolean isClientSide() {
        return false;
    }

    /**
     * Get additional object (multiline argument) from user input
     * @return The resulting object.
     */
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner) {
        return null;
    }

    /**
     * Perform action
     * @param args List of command arguments
     * @param objectArgument Additional argument object
     * @param context Current context
     * @throws InvalidCommandArgumentException If arguments are invalid or cause some errors
     * @throws ValidationException If user's input is invalid
     * @throws IOException On internal IO errors (usually related to files)
     */
    public abstract void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) throws InvalidCommandArgumentException, ValidationException, IOException;

    public String getName() {
        return this.name;
    }

    public abstract String getDescription();

    public abstract String getSyntax();

    public String getHelp() {
        return this.getDescription() + " Syntax: " + this.getSyntax();
    }
}
