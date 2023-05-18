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
    public Serializable getAdditionalObjectFromUser(PrintWriter printWriter, Scanner scanner) {
        Form locationForm = CredentialsFormCreator.getForm(printWriter);

        var credentials = new Credentials();

        locationForm.fillObjectWithValidatedUserInput(scanner, credentials);

        return credentials;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        this.clientContext.setCredentials((Credentials) objectArgument);
    }

    @Override
    public String getDescription() {
        return "Sign in";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
