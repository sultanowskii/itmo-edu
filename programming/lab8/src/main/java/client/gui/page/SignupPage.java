package client.gui.page;

import client.gui.component.CredentialsForm;
import client.runtime.ClientContext;
import lib.command.parse.CommandExecution;
import lib.command.parse.CommandInputInfo;

import java.awt.*;

public class SignupPage extends Page {
    private ClientContext context;
    private CredentialsForm credentialsForm;

    public SignupPage(ClientContext context) {
        this.context = context;
        this.credentialsForm = new CredentialsForm(this.context);

        this.credentialsForm.addSubmitListener(
            credentials -> {
                var commandExecution = new CommandExecution(
                    new CommandInputInfo(
                        "signup",
                        new String[]{},
                        credentials
                    )
                );
                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                    commandExecution
                );
            }
        );

        this.add(this.credentialsForm, BorderLayout.WEST);
    }

    @Override
    public void redraw() {
        this.credentialsForm.redraw();
    }
}
