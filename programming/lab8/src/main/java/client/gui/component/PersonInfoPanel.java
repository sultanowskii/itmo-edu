package client.gui.component;

import client.runtime.ClientContext;
import lib.schema.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PersonInfoPanel extends JPanel implements Redrawable {
    private final ClientContext context;
    private final JLabel idSignLabel = new JLabel();
    private final JLabel idLabel = new JLabel();
    private final JLabel ownerIDSignLabel = new JLabel();
    private final JLabel ownerIDLabel = new JLabel();
    private final JLabel nameSignLabel = new JLabel();
    private final JLabel nameLabel = new JLabel();
    private final JLabel coordinatesSignLabel = new JLabel();
    private final JPanel coordinatesPanel = new JPanel();
    private final JLabel coordinatesXSignLabel = new JLabel("X");
    private final JLabel coordinatesXLabel = new JLabel();
    private final JLabel coordinatesYSignLabel = new JLabel("Y");
    private final JLabel coordinatesYLabel = new JLabel();
    private final JLabel heightSignLabel = new JLabel();
    private final JLabel heightLabel = new JLabel();
    private final JLabel passportIDLabel = new JLabel();
    private final JLabel passportIDSignLabel = new JLabel();
    private final JLabel eyeColorLabel = new JLabel();
    private final JLabel eyeColorSignLabel = new JLabel();
    private final JLabel nationalitySignLabel = new JLabel();
    private final JLabel nationalityLabel = new JLabel();
    private final JLabel locationSignLabel = new JLabel();
    private final JPanel locationPanel = new JPanel();
    private final JLabel locationNameSignLabel = new JLabel();
    private final JLabel locationNameLabel = new JLabel();
    private final JLabel locationXSignLabel = new JLabel("X");
    private final JLabel locationXLabel = new JLabel();
    private final JLabel locationYSignLabel = new JLabel("Y");
    private final JLabel locationYLabel = new JLabel();

    public PersonInfoPanel(ClientContext context) {
        super();

        this.context = context;

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(4, 4, 4, 4));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        int innerGap = 5;

        this.coordinatesPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(java.awt.Color.black, 1),
                        BorderFactory.createEmptyBorder(innerGap, innerGap, innerGap, innerGap)
                )
        );
        this.coordinatesPanel.setLayout(new BoxLayout(this.coordinatesPanel, BoxLayout.Y_AXIS));
        {
            this.coordinatesPanel.add(this.coordinatesXSignLabel);
            this.coordinatesPanel.add(this.coordinatesXLabel);
            this.coordinatesPanel.add(this.coordinatesYSignLabel);
            this.coordinatesPanel.add(this.coordinatesYLabel);
        }

        this.locationPanel.setLayout(new BoxLayout(this.locationPanel, BoxLayout.Y_AXIS));
        this.locationPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(java.awt.Color.black, 1),
                        BorderFactory.createEmptyBorder(innerGap, innerGap, innerGap, innerGap)
                )
        );
        {
            this.locationPanel.add(this.locationNameSignLabel);
            this.locationPanel.add(this.locationNameLabel);
            this.locationPanel.add(this.locationXSignLabel);
            this.locationPanel.add(this.locationXLabel);
            this.locationPanel.add(this.locationYSignLabel);
            this.locationPanel.add(this.locationYLabel);
        }

        var defaultFont = idLabel.getFont();
        var signFont = defaultFont.deriveFont(Font.BOLD);

        this.idSignLabel.setFont(signFont);
        this.ownerIDSignLabel.setFont(signFont);
        this.nameSignLabel.setFont(signFont);
        this.coordinatesSignLabel.setFont(signFont);
        this.coordinatesXSignLabel.setFont(signFont);
        this.coordinatesYSignLabel.setFont(signFont);
        this.heightSignLabel.setFont(signFont);
        this.passportIDSignLabel.setFont(signFont);
        this.eyeColorSignLabel.setFont(signFont);
        this.nationalitySignLabel.setFont(signFont);
        this.locationSignLabel.setFont(signFont);
        this.locationNameSignLabel.setFont(signFont);
        this.locationYSignLabel.setFont(signFont);
        this.locationXSignLabel.setFont(signFont);

        gbc.gridy = 0;
        this.add(this.idSignLabel, gbc);
        gbc.gridy++;
        this.add(this.idLabel, gbc);
        gbc.gridy++;
        this.add(this.ownerIDSignLabel, gbc);
        gbc.gridy++;
        this.add(this.ownerIDLabel, gbc);
        gbc.gridy++;
        this.add(this.nameSignLabel, gbc);
        gbc.gridy++;
        this.add(this.nameLabel, gbc);
        gbc.gridy++;
        this.add(this.coordinatesSignLabel, gbc);
        gbc.gridy++;
        this.add(this.coordinatesPanel, gbc);
        gbc.gridy++;
        this.add(this.heightSignLabel, gbc);
        gbc.gridy++;
        this.add(this.heightLabel, gbc);
        gbc.gridy++;
        this.add(this.passportIDSignLabel, gbc);
        gbc.gridy++;
        this.add(this.passportIDLabel, gbc);
        gbc.gridy++;
        this.add(this.eyeColorSignLabel, gbc);
        gbc.gridy++;
        this.add(this.eyeColorLabel, gbc);
        gbc.gridy++;
        this.add(this.nationalitySignLabel, gbc);
        gbc.gridy++;
        this.add(this.nationalityLabel, gbc);
        gbc.gridy++;
        this.add(this.locationSignLabel, gbc);
        gbc.gridy++;
        this.add(this.locationPanel, gbc);

        this.showPerson(null);
        this.redraw();
    }

    @Override
    public void redraw() {
        var messageBundle = this.context.getLocalizationManager().getMessageBundle();
        this.idSignLabel.setText(messageBundle.getString("gui.label.id"));
        this.ownerIDSignLabel.setText(messageBundle.getString("gui.label.ownerID"));
        this.nameSignLabel.setText(messageBundle.getString("gui.label.name"));
        this.coordinatesSignLabel.setText(messageBundle.getString("gui.label.coordinates"));
        this.heightSignLabel.setText(messageBundle.getString("gui.label.height"));
        this.passportIDSignLabel.setText(messageBundle.getString("gui.label.passportID"));
        this.eyeColorSignLabel.setText(messageBundle.getString("gui.label.eyeColor"));
        this.nationalitySignLabel.setText(messageBundle.getString("gui.label.nationality"));
        this.locationSignLabel.setText(messageBundle.getString("gui.label.location"));
        this.locationNameSignLabel.setText(messageBundle.getString("gui.label.name"));
    }

    public void showPerson(Person person) {
        if (person == null) {
            this.idLabel.setText("-");
            this.ownerIDLabel.setText("-");
            this.nameLabel.setText("-");
            this.coordinatesXLabel.setText("-");
            this.coordinatesYLabel.setText("-");
            this.heightLabel.setText("-");
            this.passportIDLabel.setText("-");
            this.eyeColorLabel.setText("-");
            this.nationalityLabel.setText("-");
            this.locationNameLabel.setText("-");
            this.locationXLabel.setText("-");
            this.locationYLabel.setText("-");
        } else {
            this.idLabel.setText(Integer.toString(person.getID()));
            this.ownerIDLabel.setText(Integer.toString(person.getOwnerID()));
            this.nameLabel.setText(person.getName());
            this.coordinatesXLabel.setText(Float.toString(person.getCoordinatesX()));
            this.coordinatesYLabel.setText(Integer.toString(person.getCoordinatesY()));
            this.heightLabel.setText(Long.toString(person.getHeight()));
            this.passportIDLabel.setText(person.getPassportID());
            this.eyeColorLabel.setText(person.getEyeColor().name());
            this.nationalityLabel.setText(person.getNationality().name());
            this.locationNameLabel.setText(person.getLocationName());
            this.locationXLabel.setText(Double.toString(person.getLocationX()));
            this.locationYLabel.setText(Integer.toString(person.getLocationY()));
        }
    }
}
