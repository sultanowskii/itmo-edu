package client.command;

import client.runtime.ClientContext;
import lib.auth.Credentials;
import lib.command.Command;
import lib.form.CredentialsFormCreator;
import lib.form.Form;
import server.runtime.Context;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SigninCommand extends Command {
    private final ClientContext clientContext;

    public SigninCommand(ClientContext clientContext) {
        super("signin");
        this.clientContext = clientContext;
    }

    @Override
    public boolean isClientSide() {
        return true;
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
        this.clientContext.getCredentialsManager().setCredentials((Credentials) objectArgument);
        return true;
    }

    @Override
    public String getDescription() {
        return "Sign in";
    }

    @Override
    public String getSyntax() {
        return this.getName() + " {credentials}";
    }
}
