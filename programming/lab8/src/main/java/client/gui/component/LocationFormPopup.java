package client.gui.component;

import client.runtime.ClientContext;
import lib.schema.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LocationFormPopup extends JPanel implements Redrawable {
    private ClientContext context;
    private Consumer<Location> submitListener;
    private JDialog popup;
    private JPanel panel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel xLabel;
    private FloatField xField;
    private JLabel yLabel;
    private IntegerField yField;
    private JButton submitButton;

    public LocationFormPopup(ClientContext context) {
        super();
        this.context = context;

        this.setLayout(new GridBagLayout());
        var innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));
        innerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(java.awt.Color.black, 2),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        );

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        this.panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(java.awt.Color.black, 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        );
        {
            this.nameLabel = new JLabel();
            this.nameField = new JTextField();
            this.xLabel = new JLabel("X");
            this.xField = new FloatField();
            this.yLabel = new JLabel("Y");
            this.yField = new IntegerField();
            this.panel.add(this.nameLabel);
            this.panel.add(this.nameField);
            this.panel.add(this.xLabel);
            this.panel.add(this.xField);
            this.panel.add(this.yLabel);
            this.panel.add(this.yField);
        }

        this.submitButton = new JButton();

        this.submitButton.addActionListener(
                actionEvent -> {
                    var location = new Location();
                    location.setName(this.nameField.getText());
                    location.setX(Double.parseDouble(this.xField.getText()));
                    location.setY(Integer.parseInt(this.yField.getText()));

                    this.notifySubmitListener(location);
                }
        );

        innerPanel.add(this.panel);
        innerPanel.add(this.submitButton);

        this.add(innerPanel);

        this.popup = new JDialog();
        this.popup.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.popup.setLayout(new BorderLayout());
        this.popup.setSize(240, 400);
        this.popup.add(this, BorderLayout.CENTER);

        this.redraw();
    }

    public void redraw() {
        var messageBundle = this.context.getLocalizationManager().getMessageBundle();
        this.nameLabel.setText(messageBundle.getString("gui.label.name"));
        this.submitButton.setText(messageBundle.getString("gui.button.submit"));
    }

    public void setSubmitListener(Consumer<Location> listener) {
        this.submitListener = listener;
    }

    public void notifySubmitListener(Location event) {
        submitListener.accept(event);
    }

    public void showFrame() {
        this.popup.setVisible(true);
    }

    public void hideFrame() {
        this.popup.setVisible(false);
    }

    public void fillFields(Location location) {
        this.nameField.setText(location.getName());
        this.xField.setText(Double.toString(location.getX()));
        this.yField.setText(Integer.toString(location.getY()));
    }
}
