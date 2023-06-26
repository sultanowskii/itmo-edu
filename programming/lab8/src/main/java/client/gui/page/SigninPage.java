package client.gui.page;

import client.gui.component.CredentialsForm;
import client.runtime.ClientContext;
import lib.command.parse.CommandExecution;
import lib.command.parse.CommandInputInfo;

import javax.swing.*;
import java.awt.*;

public class SigninPage extends Page {
    private ClientContext context;
    private CredentialsForm credentialsForm;

    public SigninPage(ClientContext context) {
        this.context = context;
        this.credentialsForm = new CredentialsForm(this.context);

        this.credentialsForm.addSubmitListener(
            credentials -> {
                var commandExecutionGetUserID = new CommandExecution(
                        new CommandInputInfo(
                                "get_user_id",
                                new String[]{credentials.getLogin()}
                        )
                );
                commandExecutionGetUserID.addLitstener(
                        result -> {
                            JOptionPane.showMessageDialog(null, result.getMessage());
                            if (result.isSuccess()) {
                                this.context.setUserID(Integer.parseInt(result.getMessage()));
                            }
                        }
                );
                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                        commandExecutionGetUserID
                );

                var commandExecutionSignin = new CommandExecution(
                    new CommandInputInfo(
                        "signin",
                        new String[]{},
                        credentials
                    )
                );
                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                        commandExecutionSignin
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
