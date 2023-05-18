package lib.command;

import lib.auth.Credentials;
import lib.auth.Hasher;
import lib.form.CredentialsFormCreator;
import lib.form.Form;
import server.runtime.Context;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Scanner;

public class SignupCommand extends Command {

    public SignupCommand() {
        super("signup");
    }

    @Override
    public boolean loginRequired() {
        return false;
    }

    @Override
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner) {
        Form locationForm = CredentialsFormCreator.getForm(printWriter);

        var credentials = new Credentials();

        locationForm.fillObjectWithValidatedUserInput(scanner, credentials);

        return credentials;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        Credentials credentials = (Credentials) objectArgument;

        if (credentials.getLogin().isEmpty() || credentials.getPassword().isEmpty()) {
            printWriter.println("Permission denied. Please sign in.");
            return;
        }

        var login = credentials.getLogin();
        var hashedPassword = Hasher.sha512(credentials.getPassword());

        var db = context.getDB();

        try {
            if (!db.loginIsAvailable(login)) {
                printWriter.println("This login is already occupied.");
                return;
            }
            User addedUser = db.addUser(login, hashedPassword);
            if (addedUser == null) {
                printWriter.println("Something completely unexpected happened. Can't register user.");
                return;
            }
        } catch (SQLException e) {
            printWriter.println("DB error: " + e.getMessage());
            return;
        }
    }

    @Override
    public String getDescription() {
        return "Register a new user.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
