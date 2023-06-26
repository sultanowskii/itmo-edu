package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import lib.form.field.IntegerField;
import lib.form.validation.ValidationException;
import server.manager.PersonManager;
import server.runtime.Context;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class GetUserIDCommand extends Command {
    public GetUserIDCommand() {
        super("get_user_id");
    }

    @Override
    public boolean loginRequired() {
        return false;
    }

    @Override
    public void validateArguments(String[] args) throws InvalidCommandArgumentException {
        if (args.length != 1) {
            throw new InvalidCommandArgumentException("Syntax:\n" + this.getName() + " <login>");
        }
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) throws InvalidCommandArgumentException, ValidationException {
        this.validateArguments(args);

        var messageBundle = context.getMessageBundle();

        String username = args[0];

        int userID;
        try {
            userID = context.getDB().getUserIDByLogin(username);
        } catch (SQLException e) {
            printWriter.println(messageBundle.getString("error.db") + ": " + e.getMessage());
            return false;
        }

        printWriter.println(userID);

        return true;
    }

    @Override
    public String getDescription() {
        return "Get ID of user by login.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " login";
    }
}
