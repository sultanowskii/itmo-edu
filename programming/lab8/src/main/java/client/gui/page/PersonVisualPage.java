package client.gui.page;

import client.gui.component.*;
import client.gui.util.PersonValidator;
import client.gui.util.RandomColorGenerator;
import client.runtime.ClientContext;
import lib.command.parse.CommandExecution;
import lib.command.parse.CommandInputInfo;
import lib.manager.PersonManagerDiff;
import lib.schema.Person;
import server.manager.PersonManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class PersonVisualPage extends Page {
    private ClientContext context;
    private JPanel actionButtonsPanel;
    private JButton createButton;
    private JButton createIfMinButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton deleteByLocationButton;
    private JButton deleteGreaterButton;
    private JButton deleteLowerButton;
    private JButton clearButton;
    private JButton countGreaterThanLocationButton;
    private JButton printFieldDescendingNationalityButton;
    private PersonInfoPanel personInfoPanel;
    private PersonArea personVisualPanel;
    private final HashMap<Integer, Color> userColors = new HashMap<>();

    public PersonVisualPage(ClientContext context) {
        this.context = context;
        this.context.addPersonManagerUpdateListener(this::personManagerUpdateListener);
        this.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        {
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weightx = 0.15;
            constraints.weighty = 0.0;
            var headerLeftFiller = new JPanel();
            this.add(headerLeftFiller, constraints);
        }

        {
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.weightx = 0.85;
            constraints.weighty = 0.0;
            this.actionButtonsPanel = this.setupActionButtonsPanel();
            this.add(this.actionButtonsPanel, constraints);
        }

        {
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.weightx = 0.15;
            constraints.weighty = 1.0;
            this.personInfoPanel = this.setupPersonInfoPanel();
            this.add(this.personInfoPanel, constraints);
        }

        {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.weightx = 0.85;
            constraints.weighty = 1;
            this.personVisualPanel = this.setupPersonVisualPanel();
            this.add(this.personVisualPanel, constraints);
        }

        this.personVisualPanel.addSelectionListener(
                personVisualization -> {
                    boolean enableDetailButtons = false;

                    if (personVisualization != null) {
                        int personID = personVisualization.getPersonID();

                        Person selectedPerson;
                        try {
                            selectedPerson = context.getPersonManager().getByID(personID);
                            this.personInfoPanel.showPerson(selectedPerson);
                            enableDetailButtons = selectedPerson.getOwnerID() == context.getUserID();
                        } catch (NoSuchElementException e) {
                            enableDetailButtons = false;
                            this.personInfoPanel.showPerson(null);
                        }
                    } else {
                        this.personInfoPanel.showPerson(null);
                    }

                    this.editButton.setEnabled(enableDetailButtons);
                    this.deleteButton.setEnabled(enableDetailButtons);
                }
        );

        this.redraw();
    }

    private JPanel setupActionButtonsPanel() {
        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(new GridLayout(2, 5, 5, 5));
        actionButtonsPanel.setBorder(new EmptyBorder(4, 4, 4, 4));

        this.createButton = new JButton();
        this.createButton.addActionListener(
                action -> {
                    var personForm = new PersonFormPopup(
                            context
                    );
                    personForm.setSubmitListener(
                            person -> {
                                var validationResult = PersonValidator.getPersonErrors(context, person);
                                if (validationResult != null) {
                                    JOptionPane.showMessageDialog(null, validationResult);
                                    return;
                                }
                                var commandExecution = new CommandExecution(
                                        new CommandInputInfo(
                                                "add",
                                                new String[]{},
                                                person
                                        )
                                );
                                commandExecution.addLitstener(
                                        result -> {
                                            if (result == null) {
                                                return;
                                            }
                                            if (result.isSuccess()) {
                                                personForm.hideFrame();
                                            } else {
                                                JOptionPane.showMessageDialog(null, result.getMessage());
                                            }
                                        }
                                );
                                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                                        commandExecution
                                );
                            }
                    );
                    personForm.showFrame();
                }
        );

        this.createIfMinButton = new JButton();
        this.createIfMinButton.addActionListener(
                action -> {
                    var personForm = new PersonFormPopup(
                            context
                    );
                    personForm.setSubmitListener(
                            person -> {
                                var validationResult = PersonValidator.getPersonErrors(context, person);
                                if (validationResult != null) {
                                    JOptionPane.showMessageDialog(null, validationResult);
                                    return;
                                }
                                var commandExecution = new CommandExecution(
                                        new CommandInputInfo(
                                                "add_if_min",
                                                new String[]{},
                                                person
                                        )
                                );
                                commandExecution.addLitstener(
                                        result -> {
                                            if (result == null) {
                                                return;
                                            }
                                            if (result.isSuccess()) {
                                                personForm.hideFrame();
                                            } else {
                                                JOptionPane.showMessageDialog(null, result.getMessage());
                                            }
                                        }
                                );
                                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                                        commandExecution
                                );
                            }
                    );
                    personForm.showFrame();
                }
        );

        this.editButton = new JButton();
        this.editButton.addActionListener(
                action -> {
                    var personVisualization = personVisualPanel.getSelectedPerson();
                    if (personVisualization == null) {
                        return;
                    }
                    if (personVisualization.getOwnerID() != context.getUserID()) {
                        return;
                    }

                    var id = personVisualization.getPersonID();

                    Person personToEdit;
                    try {
                        personToEdit = context.getPersonManager().getByID(id);
                    } catch (NoSuchElementException e) {
                        return;
                    }

                    var personForm = new PersonFormPopup(
                            context
                    );
                    personForm.setSubmitListener(
                            person -> {
                                var validationResult = PersonValidator.getPersonErrors(context, person);
                                if (validationResult != null) {
                                    JOptionPane.showMessageDialog(null, validationResult);
                                    return;
                                }
                                var commandExecution = new CommandExecution(
                                        new CommandInputInfo(
                                                "update",
                                                new String[]{Integer.toString(id)},
                                                person
                                        )
                                );
                                commandExecution.addLitstener(
                                        result -> {
                                            if (result == null) {
                                                return;
                                            }
                                            if (result.isSuccess()) {
                                                personForm.hideFrame();
                                            } else {
                                                JOptionPane.showMessageDialog(null, result.getMessage());
                                            }
                                        }
                                );
                                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                                        commandExecution
                                );
                            }
                    );
                    personForm.fillFields(personToEdit);
                    personForm.showFrame();
                }
        );
        this.editButton.setEnabled(false);

        this.deleteButton = new JButton();
        this.deleteButton.addActionListener(
                action -> {
                    var personVisualization = personVisualPanel.getSelectedPerson();
                    if (personVisualization == null) {
                        return;
                    }
                    if (personVisualization.getOwnerID() != context.getUserID()) {
                        return;
                    }

                    var commandExecution = new CommandExecution(
                            new CommandInputInfo(
                                    "remove_by_id",
                                    new String[]{Integer.toString(personVisualization.getPersonID())}
                            )
                    );
                    commandExecution.addLitstener(
                            result -> {
                                if (result == null || result.isSuccess()) {
                                    return;
                                }
                                JOptionPane.showMessageDialog(null, result.getMessage());
                            }
                    );
                    this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                            commandExecution
                    );
                }
        );
        this.deleteButton.setEnabled(false);

        this.deleteByLocationButton = new JButton();
        this.deleteByLocationButton.addActionListener(
                action -> {
                    var locationForm = new LocationFormPopup(this.context);
                    locationForm.setSubmitListener(
                            location -> {
                                var commandExecution = new CommandExecution(
                                        new CommandInputInfo(
                                                "remove_all_by_location",
                                                new String[]{},
                                                location
                                        )
                                );
                                commandExecution.addLitstener(
                                        result -> {
                                            if (result == null) {
                                                return;
                                            }
                                            if (result.isSuccess()) {
                                                locationForm.hideFrame();
                                            } else {
                                                JOptionPane.showMessageDialog(null, result.getMessage());
                                            }
                                        }
                                );
                                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                                        commandExecution
                                );
                            }
                    );
                    locationForm.showFrame();
                }
        );

        this.deleteGreaterButton = new JButton();
        this.deleteGreaterButton.addActionListener(
                action -> {
                    var personForm = new PersonFormPopup(
                            context
                    );
                    personForm.setSubmitListener(
                            person -> {
                                var validationResult = PersonValidator.getPersonErrors(context, person);
                                if (validationResult != null) {
                                    JOptionPane.showMessageDialog(null, validationResult);
                                    return;
                                }
                                var commandExecution = new CommandExecution(
                                        new CommandInputInfo(
                                                "remove_greater",
                                                new String[]{},
                                                person
                                        )
                                );
                                commandExecution.addLitstener(
                                        result -> {
                                            if (result == null) {
                                                return;
                                            }
                                            if (result.isSuccess()) {
                                                personForm.hideFrame();
                                            } else {
                                                JOptionPane.showMessageDialog(null, result.getMessage());
                                            }
                                        }
                                );
                                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                                        commandExecution
                                );
                            }
                    );
                    personForm.showFrame();
                }
        );

        this.deleteLowerButton = new JButton();
        this.deleteLowerButton.addActionListener(
                action -> {
                    var personForm = new PersonFormPopup(
                            context
                    );
                    personForm.setSubmitListener(
                            person -> {
                                var validationResult = PersonValidator.getPersonErrors(context, person);
                                if (validationResult != null) {
                                    JOptionPane.showMessageDialog(null, validationResult);
                                    return;
                                }
                                var commandExecution = new CommandExecution(
                                        new CommandInputInfo(
                                                "remove_lower",
                                                new String[]{},
                                                person
                                        )
                                );
                                commandExecution.addLitstener(
                                        result -> {
                                            if (result == null) {
                                                return;
                                            }
                                            if (result.isSuccess()) {
                                                personForm.hideFrame();
                                            } else {
                                                JOptionPane.showMessageDialog(null, result.getMessage());
                                            }
                                        }
                                );
                                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                                        commandExecution
                                );
                            }
                    );
                    personForm.showFrame();
                }
        );

        this.clearButton = new JButton();
        this.clearButton.addActionListener(
                action -> {
                    var commandExecution = new CommandExecution(
                            new CommandInputInfo(
                                    "clear"
                            )
                    );
                    commandExecution.addLitstener(
                            result -> {
                                if (result == null || result.isSuccess()) {
                                    return;
                                }
                                JOptionPane.showMessageDialog(null, result.getMessage());
                            }
                    );
                    this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                            commandExecution
                    );
                }
        );

        this.countGreaterThanLocationButton = new JButton();
        this.countGreaterThanLocationButton.addActionListener(
                action -> {
                    var locationForm = new LocationFormPopup(this.context);
                    locationForm.setSubmitListener(
                            location -> {
                                var commandExecution = new CommandExecution(
                                        new CommandInputInfo(
                                                "count_greater_than_location",
                                                new String[]{},
                                                location
                                        )
                                );
                                commandExecution.addLitstener(
                                        result -> {
                                            if (result == null) {
                                                return;
                                            }
                                            JOptionPane.showMessageDialog(null, result.getMessage());
                                            locationForm.hideFrame();
                                        }
                                );
                                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                                        commandExecution
                                );
                            }
                    );
                    locationForm.showFrame();
                }
        );

        this.printFieldDescendingNationalityButton = new JButton();
        this.printFieldDescendingNationalityButton.addActionListener(
                action -> {
                    var commandExecution = new CommandExecution(
                            new CommandInputInfo(
                                    "print_field_descending_nationality"
                            )
                    );
                    commandExecution.addLitstener(
                            result -> {
                                if (result != null) {
                                    JOptionPane.showMessageDialog(null, result.getMessage());
                                }
                            }
                    );
                    this.context.getCommandQueueExecutor().addCommandExecutionToQueue(
                            commandExecution
                    );
                }
        );

        actionButtonsPanel.add(createButton);
        actionButtonsPanel.add(createIfMinButton);
        actionButtonsPanel.add(editButton);
        actionButtonsPanel.add(deleteButton);
        actionButtonsPanel.add(deleteByLocationButton);
        actionButtonsPanel.add(deleteGreaterButton);
        actionButtonsPanel.add(deleteLowerButton);
        actionButtonsPanel.add(clearButton);
        actionButtonsPanel.add(countGreaterThanLocationButton);
        actionButtonsPanel.add(printFieldDescendingNationalityButton);

        return actionButtonsPanel;
    }

    private PersonInfoPanel setupPersonInfoPanel() {
        return new PersonInfoPanel(this.context);
    }

    private PersonArea setupPersonVisualPanel() {
        PersonArea humanArea = new PersonArea();
        humanArea.setBorder(new EmptyBorder(4, 4, 4, 4));

        return humanArea;
    }

    @Override
    public void redraw() {
        var messageBundle = context.getLocalizationManager().getMessageBundle();

        this.createButton.setText(messageBundle.getString("gui.button.create"));
        this.createIfMinButton.setText(messageBundle.getString("gui.button.createIfMin"));
        this.editButton.setText(messageBundle.getString("gui.button.edit"));
        this.deleteButton.setText(messageBundle.getString("gui.button.delete"));
        this.deleteByLocationButton.setText(messageBundle.getString("gui.button.deleteByLocation"));
        this.deleteGreaterButton.setText(messageBundle.getString("gui.button.deleteGreater"));
        this.deleteLowerButton.setText(messageBundle.getString("gui.button.deleteLower"));
        this.clearButton.setText(messageBundle.getString("gui.button.clear"));
        this.countGreaterThanLocationButton.setText(messageBundle.getString("gui.button.countGreaterThanLocation"));
        this.printFieldDescendingNationalityButton.setText(messageBundle.getString("gui.button.printFieldDescendingNationality"));

        this.personInfoPanel.redraw();
    }

    public void personManagerUpdateListener(PersonManager oldPersonManager, PersonManager newPersonManager) {
        this.reloadArea(oldPersonManager, newPersonManager);
    }

    public Color getUserColor(int ownerID) {
        if (!userColors.containsKey(ownerID)) {
            userColors.put(ownerID, RandomColorGenerator.generateRandomColor());
        }
        return userColors.get(ownerID);
    }

    public void reloadArea(PersonManager oldPersonManager, PersonManager newPersonManager) {
        PersonManagerDiff diff = new PersonManagerDiff(oldPersonManager, newPersonManager);

        int centerX = this.personVisualPanel.getWidth() / 2;
        int centerY = this.personVisualPanel.getHeight() / 2;

        var addedPersons = diff.getAddedPersons();
        for (var personID : addedPersons.keySet()) {
            var person = addedPersons.get(personID);
            var personVisualization = new PersonArea.PersonVisualization(
                    person,
                    this.getUserColor(person.getOwnerID())
            );
            this.personVisualPanel.addPersonWithAnimation(personVisualization);
        }

        var editedPersons = diff.getEditedPersons();
        for (var personID : editedPersons.keySet()) {
            var person = addedPersons.get(personID);

            this.personVisualPanel.replacePersonByID(
                    personID,
                    new PersonArea.PersonVisualization(
                            person,
                            this.getUserColor(person.getOwnerID())
                    )
            );
        }

        for (var personToDeleteID : diff.getDeletedPersons().keySet()) {
            this.personVisualPanel.removePersonByID(personToDeleteID);
        }

        this.personVisualPanel.repaint();
    }
}
