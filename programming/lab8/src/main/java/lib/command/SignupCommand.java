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
import java.util.ResourceBundle;
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
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner, ResourceBundle messageBundle) {
        Form locationForm = CredentialsFormCreator.getForm(printWriter);

        var credentials = new Credentials();

        locationForm.fillObjectWithValidatedUserInput(scanner, credentials, messageBundle);

        return credentials;
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        Credentials credentials = (Credentials) objectArgument;
        var messageBundle = context.getMessageBundle();

        if (credentials.getLogin().isEmpty() || credentials.getPassword().isEmpty()) {
            printWriter.println(messageBundle.getString("error.signin"));
            return false;
        }

        var login = credentials.getLogin();
        var hashedPassword = Hasher.sha512(credentials.getPassword(), messageBundle);

        var db = context.getDB();

        try {
            if (!db.loginIsAvailable(login)) {
                printWriter.println(messageBundle.getString("error.loginOccupied"));
                return false;
            }
            User addedUser = db.addUser(login, hashedPassword);
            if (addedUser == null) {
                printWriter.println(messageBundle.getString("error.cantRegisterUser"));
                return false;
            }
        } catch (SQLException e) {
            printWriter.println(messageBundle.getString("error.db") + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Register a new user.";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {credentials}";
    }
}
