package client.gui.component;

import client.runtime.ClientContext;
import lib.schema.*;
import lib.schema.Color;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class PersonFormPopup extends JPanel implements Redrawable {
    private ClientContext context;
    private Consumer<Person> submitListener;
    private JDialog popup;
    private JLabel idLabel;
    private IntegerField idField;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel coordinatesLabel;
    private JPanel coordinatesPanel;
    private JLabel coordinatesXLabel;
    private FloatField coordinatesXField;
    private JLabel coordinatesYLabel;
    private IntegerField coordinatesYField;
    private JLabel heightLabel;
    private IntegerField heightField;
    private JLabel passportIDLabel;
    private JTextField passportIDField;
    private JLabel eyeColorLabel;
    private JComboBox<Color> eyeColorField;
    private JLabel nationalityLabel;
    private JComboBox<Country> nationalityField;
    private JLabel locationLabel;
    private JPanel locationPanel;
    private JLabel locationNameLabel;
    private JTextField locationNameField;
    private JLabel locationXLabel;
    private FloatField locationXField;
    private JLabel locationYLabel;
    private IntegerField locationYField;
    private JButton submitButton;

    public PersonFormPopup(ClientContext context) {
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

        this.idLabel = new JLabel();
        this.idField = new IntegerField();
        this.idField.setEnabled(false);
        this.nameLabel = new JLabel();
        this.nameField = new JTextField();

        this.coordinatesLabel = new JLabel();
        this.coordinatesPanel = new JPanel();
        this.coordinatesPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(java.awt.Color.black, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            )
        );
        this.coordinatesPanel.setLayout(new BoxLayout(this.coordinatesPanel, BoxLayout.Y_AXIS));
        {
            this.coordinatesXLabel = new JLabel("X");
            this.coordinatesXField = new FloatField();
            this.coordinatesYLabel = new JLabel("Y");
            this.coordinatesYField = new IntegerField();

            this.coordinatesPanel.add(this.coordinatesXLabel);
            this.coordinatesPanel.add(this.coordinatesXField);
            this.coordinatesPanel.add(this.coordinatesYLabel);
            this.coordinatesPanel.add(this.coordinatesYField);
        }

        this.heightLabel = new JLabel();
        this.heightField = new IntegerField();
        this.passportIDLabel = new JLabel();
        this.passportIDField = new JTextField();
        this.eyeColorLabel = new JLabel();
        this.eyeColorField = new JComboBox<>(Color.values());
        this.nationalityLabel = new JLabel();
        this.nationalityField = new JComboBox<>(Country.values());

        this.locationLabel = new JLabel();
        this.locationPanel = new JPanel();
        this.locationPanel.setLayout(new BoxLayout(this.locationPanel, BoxLayout.Y_AXIS));
        this.locationPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(java.awt.Color.black, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            )
        );
        {
            this.locationNameLabel = new JLabel();
            this.locationNameField = new JTextField();
            this.locationXLabel = new JLabel("X");
            this.locationXField = new FloatField();
            this.locationYLabel = new JLabel("Y");
            this.locationYField = new IntegerField();
            this.locationPanel.add(this.locationNameLabel);
            this.locationPanel.add(this.locationNameField);
            this.locationPanel.add(this.locationXLabel);
            this.locationPanel.add(this.locationXField);
            this.locationPanel.add(this.locationYLabel);
            this.locationPanel.add(this.locationYField);
        }

        this.submitButton = new JButton();

        this.submitButton.addActionListener(
            actionEvent -> {
                var coordinates = new Coordinates();
                coordinates.setX(Float.parseFloat(this.coordinatesXField.getText()));
                coordinates.setY(Integer.parseInt(this.coordinatesYField.getText()));

                var location = new Location();
                location.setName(this.locationNameField.getText());
                location.setX(Double.parseDouble(this.locationXField.getText()));
                location.setY(Integer.parseInt(this.locationYField.getText()));

                var person = new Person();
                person.setName(this.nameField.getText());
                person.setCoordinates(coordinates);
                person.setHeight(Long.parseLong(this.heightField.getText()));
                person.setPassportID(this.passportIDField.getText());
                person.setEyeColor((Color)this.eyeColorField.getSelectedItem());
                person.setNationality((Country)this.nationalityField.getSelectedItem());
                person.setLocation(location);

                this.notifySubmitListener(person);
            }
        );

        innerPanel.add(this.idLabel);
        innerPanel.add(this.idField);
        innerPanel.add(this.nameLabel);
        innerPanel.add(this.nameField);
        innerPanel.add(this.coordinatesLabel);
        innerPanel.add(this.coordinatesPanel);
        innerPanel.add(this.heightLabel);
        innerPanel.add(this.heightField);
        innerPanel.add(this.passportIDLabel);
        innerPanel.add(this.passportIDField);
        innerPanel.add(this.eyeColorLabel);
        innerPanel.add(this.eyeColorField);
        innerPanel.add(this.nationalityLabel);
        innerPanel.add(this.nationalityField);
        innerPanel.add(this.locationLabel);
        innerPanel.add(this.locationPanel);
        innerPanel.add(this.submitButton);

        this.add(innerPanel);

        this.popup = new JDialog();
        this.popup.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.popup.setLayout(new BorderLayout());
        this.popup.setSize(400, 700);
        this.popup.add(this, BorderLayout.CENTER);

        this.redraw();
    }

    public void redraw() {
        var messageBundle = this.context.getLocalizationManager().getMessageBundle();
        this.idLabel.setText(messageBundle.getString("gui.label.id"));
        this.nameLabel.setText(messageBundle.getString("gui.label.name"));
        this.coordinatesLabel.setText(messageBundle.getString("gui.label.coordinates"));
        this.heightLabel.setText(messageBundle.getString("gui.label.height"));
        this.passportIDLabel.setText(messageBundle.getString("gui.label.passportID"));
        this.eyeColorLabel.setText(messageBundle.getString("gui.label.eyeColor"));
        this.nationalityLabel.setText(messageBundle.getString("gui.label.nationality"));
        this.locationLabel.setText(messageBundle.getString("gui.label.location"));
        this.locationNameLabel.setText(messageBundle.getString("gui.label.name"));
        this.submitButton.setText(messageBundle.getString("gui.button.submit"));
    }

    public void setSubmitListener(Consumer<Person> listener) {
        this.submitListener = listener;
    }

    public void notifySubmitListener(Person event) {
        submitListener.accept(event);
    }

    public void showFrame() {
        this.popup.setVisible(true);
    }

    public void hideFrame() {
        this.popup.setVisible(false);
    }

    public void fillFields(Person person) {
        this.idField.setText(Integer.toString(person.getID()));
        this.nameField.setText(person.getName());
        this.coordinatesXField.setText(Float.toString(person.getCoordinates().getX()));
        this.coordinatesYField.setText(Integer.toString(person.getCoordinates().getY()));
        this.heightField.setText(Long.toString(person.getHeight()));
        this.passportIDField.setText(person.getPassportID());
        this.eyeColorField.setSelectedItem(person.getEyeColor());
        this.nationalityField.setSelectedItem(person.getNationality());
        this.locationNameField.setText(person.getLocation().getName());
        this.locationXField.setText(Double.toString(person.getLocation().getX()));
        this.locationYField.setText(Integer.toString(person.getCoordinates().getY()));
    }
}
