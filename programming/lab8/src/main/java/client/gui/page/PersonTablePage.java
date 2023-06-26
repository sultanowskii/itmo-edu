package client.gui.page;

import client.gui.component.LocationFormPopup;
import client.gui.component.PersonFormPopup;
import client.gui.util.PersonValidator;
import client.runtime.ClientContext;
import lib.command.parse.CommandExecution;
import lib.command.parse.CommandInputInfo;
import lib.schema.Person;
import lib.schema.util.PersonComparison;
import lib.schema.util.PersonFieldSearcher;
import server.manager.PersonManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Vector;

public class PersonTablePage extends Page {
    private final static String[] COLUMN_NAME_KEYS = {
            "gui.label.id",
            "gui.label.name",
            "gui.label.coordinatesX",
            "gui.label.coordinatesY",
            "gui.label.height",
            "gui.label.passportID",
            "gui.label.eyeColor",
            "gui.label.nationality",
            "gui.label.locationName",
            "gui.label.locationX",
            "gui.label.locationY"
    };
    private ClientContext context;
    private JPanel tablePanel;
    private JTable table;
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
    private JPanel filterPanel;
    private JLabel searchValueLabel;
    private JTextField searchValueField;
    private JLabel searchFieldLabel;
    private JComboBox<String> searchFieldField;
    private JLabel sortFieldLabel;
    private JComboBox<String> sortFieldField;

    public PersonTablePage(ClientContext context) {
        this.context = context;

        this.context.addPersonManagerUpdateListener(this::personManagerUpdateListener);

        this.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        // header filler
        {
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.weightx = 0.15;
            constraints.weighty = 0.0;
            var headerLeftFiller = new JPanel();
            this.add(headerLeftFiller, constraints);
        }

        {
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.weightx = 0.85;
            constraints.weighty = 0.0;
            this.actionButtonsPanel = this.setupActionButtonsPanel();
            this.add(this.actionButtonsPanel, constraints);
        }

        {
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.weightx = 0.15;
            constraints.weighty = 1.0;
            this.filterPanel = this.setupFilterPanel();
            this.add(this.filterPanel, constraints);
        }

        {
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.weightx = 0.85;
            this.tablePanel = this.setupTablePanel();
            this.add(this.tablePanel, constraints);
        }

        this.table.getSelectionModel().addListSelectionListener(
                action -> {
                    int row = this.table.getSelectedRow();

                    boolean enableDetailButtons;

                    if (row == -1) {
                        enableDetailButtons = false;
                    } else {
                        int personID = (int) table.getValueAt(row, 0);

                        Person selectedPerson;
                        try {
                            selectedPerson = context.getPersonManager().getByID(personID);
                            enableDetailButtons = selectedPerson.getOwnerID() == context.getUserID();
                        } catch (NoSuchElementException e) {
                            enableDetailButtons = false;
                        }
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
                    int selectedRow = this.table.getSelectedRow();
                    if (selectedRow == -1) {
                        return;
                    }
                    int id = (int) table.getValueAt(selectedRow, 0);

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
                    int selectedRow = this.table.getSelectedRow();
                    if (selectedRow != -1) {
                        int id = (int) table.getValueAt(selectedRow, 0);
                        var commandExecution = new CommandExecution(
                                new CommandInputInfo(
                                        "remove_by_id",
                                        new String[]{Integer.toString(id)}
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

    private JPanel setupTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(4, 4, 4, 4));

        String[] columnNames = {"ID", "Name", "Coordinates.x", "Coordinates.y", "Height", "Passport ID",
                "Eye color", "Nationality", "Location.name", "Location.x", "Location.y"};

        this.table = new JTable(new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int col, int row) {
                return false;
            }
        });
        table.setFillsViewportHeight(true);

        var scrollPane = new JScrollPane(table);

        ((DefaultTableModel) table.getModel()).setRowCount(0);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel setupFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(new EmptyBorder(4, 4, 4, 4));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        this.sortFieldLabel = new JLabel();
        gbc.gridy = 0;
        filterPanel.add(this.sortFieldLabel, gbc);

        this.sortFieldField = new JComboBox<>(PersonComparison.FIELDS);
        this.sortFieldField.addActionListener(
                e -> this.reloadTable()
        );
        gbc.gridy = 1;
        filterPanel.add(this.sortFieldField, gbc);

        this.searchFieldLabel = new JLabel();
        gbc.gridy = 2;
        filterPanel.add(this.searchFieldLabel, gbc);

        this.searchFieldField = new JComboBox<>(PersonFieldSearcher.FIELDS);
        this.searchFieldField.addActionListener(
                e -> this.reloadTable()
        );
        gbc.gridy = 3;
        filterPanel.add(this.searchFieldField, gbc);

        this.searchValueLabel = new JLabel();
        gbc.gridy = 4;
        filterPanel.add(this.searchValueLabel, gbc);

        this.searchValueField = new JTextField();
        this.searchValueField.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent documentEvent) {
                        handleChange();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent documentEvent) {
                        handleChange();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent documentEvent) {
                        handleChange();
                    }

                    public void handleChange() {
                        reloadTable();
                    }
                }
        );
        gbc.gridy = 5;
        filterPanel.add(this.searchValueField, gbc);

        return filterPanel;
    }

    @Override
    public void redraw() {
        var messageBundle = context.getLocalizationManager().getMessageBundle();
        ((DefaultTableModel) this.table.getModel()).setColumnIdentifiers(getColumnNames(messageBundle));

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

        this.sortFieldLabel.setText(messageBundle.getString("gui.label.sortField"));
        this.searchFieldLabel.setText(messageBundle.getString("gui.label.searchField"));
        this.searchValueLabel.setText(messageBundle.getString("gui.label.searchValue"));
    }

    public String[] getColumnNames(ResourceBundle messageBundle) {
        return Arrays.stream(COLUMN_NAME_KEYS).map(messageBundle::getString).toArray(String[]::new);
    }

    private static void addRow(JTable table, Object... rowData) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Vector<>(java.util.Arrays.asList(rowData)));
    }

    public void personManagerUpdateListener(PersonManager oldPersonManager, PersonManager newPersonManager) {
        this.reloadTable();
    }

    public void reloadTable() {
        var personManager = context.getPersonManager();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String searchItem = this.searchValueField.getText();
        String searchField = (String) this.searchFieldField.getSelectedItem();
        String sortField = (String) this.sortFieldField.getSelectedItem();
        var storage = personManager.getStorage()
                .stream()
                .filter(PersonFieldSearcher.fieldContainsValue(searchField, searchItem))
                .sorted(PersonComparison.getFieldComparator(sortField))
                .toList();

        for (var person : storage) {
            var coordinates = person.getCoordinates();
            var location = person.getLocation();
            addRow(
                    this.table,
                    person.getID(),
                    person.getName(),
                    coordinates.getX(),
                    coordinates.getY(),
                    person.getHeight(),
                    person.getPassportID(),
                    person.getEyeColor(),
                    person.getNationality(),
                    location.getName(),
                    location.getX(),
                    location.getY()
            );
        }
    }
}
