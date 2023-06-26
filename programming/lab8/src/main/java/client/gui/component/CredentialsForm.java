package client.gui.component;

import client.runtime.ClientContext;
import lib.auth.Credentials;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CredentialsForm extends JPanel implements Redrawable {
    private ClientContext context;
    private List<Consumer<Credentials>> submitListeners;
    private JDialog popup;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton submitButton;

    public CredentialsForm(ClientContext context) {
        super();
        this.submitListeners = new CopyOnWriteArrayList<>();
        this.context = context;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.usernameLabel = new JLabel();
        this.usernameField = new JTextField();
        this.passwordLabel = new JLabel();
        this.passwordField = new JPasswordField();
        this.submitButton = new JButton();
        this.submitButton.addActionListener(
            actionEvent -> {
                this.notifySubmitListeners(
                    new Credentials(
                        this.usernameField.getText(),
                        new String(this.passwordField.getPassword())
                    )
                );
                this.popup.setVisible(false);
            }
        );

        this.add(this.usernameLabel);
        this.add(this.usernameField);
        this.add(this.passwordLabel);
        this.add(this.passwordField);
        this.add(this.submitButton);

        this.redraw();

        this.popup = new JDialog();
        this.popup.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.popup.setLayout(new BorderLayout());
        this.popup.setSize(400, 700);
        this.popup.add(this, BorderLayout.CENTER);
    }

    public void redraw() {
        var messageBundle = this.context.getLocalizationManager().getMessageBundle();
        this.usernameLabel.setText(messageBundle.getString("gui.label.username"));
        this.passwordLabel.setText(messageBundle.getString("gui.label.password"));
        this.submitButton.setText(messageBundle.getString("gui.button.submit"));
    }

    public void showPopup() {
        this.popup.setVisible(true);
    }

    public void addSubmitListener(Consumer<Credentials> listener) {
        this.submitListeners.add(listener);
    }

    public void notifySubmitListeners(Credentials event) {
        for (var listener : submitListeners) {
            listener.accept(event);
        }
    }
}
