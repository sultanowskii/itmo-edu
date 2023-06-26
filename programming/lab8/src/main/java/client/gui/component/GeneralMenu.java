package client.gui.component;

import client.gui.page.PageChanger;
import client.gui.page.Pages;
import client.runtime.ClientContext;
import lib.command.parse.CommandExecution;
import lib.command.parse.CommandInputInfo;

import javax.swing.*;
import java.awt.*;

public class GeneralMenu extends JMenuBar implements Redrawable {
    private final JFrame parentFrame;
    private final PageChanger pageChanger;
    private final ClientContext context;
    private final JMenu accountMenu;
    private final JMenuItem signinMenuItem;
    private final JMenuItem signupMenuItem;
    private final JMenu viewMenu;
    private final JMenuItem tableViewMenuItem;
    private final JMenuItem visualViewMenuItem;
    private final JMenu infoMenu;
    private final JMenuItem infoMenuItem;
    private final JMenu settingsMenu;
    private final JMenuItem languageMenuItem;
    private final JMenu helpMenu;
    private final JMenuItem cliCommandListMenuItem;
    private final JMenuItem aboutMenuItem;
    private final RightStickingLabelMenuItem usernameItem;
    private final LanguageChoicePopup languageChoicePopup;

    public GeneralMenu(JFrame parentFrame, PageChanger pageChanger, ClientContext context) {
        super();
        this.context = context;
        this.pageChanger = pageChanger;
        this.parentFrame = parentFrame;

        var messageBundle = context.getLocalizationManager().getMessageBundle();

        this.languageChoicePopup = new LanguageChoicePopup(context);

        var commandExecutor = this.context.getCommandQueueExecutor();

        {
            this.accountMenu = new JMenu();

            this.signinMenuItem = new JMenuItem();
            this.signinMenuItem.addActionListener(e -> {
                CredentialsForm credentialsForm = new CredentialsForm(context);
                credentialsForm.addSubmitListener(
                    credentials -> {
                        var commandExecutionGetUserID = new CommandExecution(
                                new CommandInputInfo(
                                        "get_user_id",
                                        new String[]{credentials.getLogin()}
                                )
                        );
                        commandExecutionGetUserID.addLitstener(
                                result -> {
                                    if (result.isSuccess()) {
                                        var commandExecutionSignin = new CommandExecution(
                                                new CommandInputInfo(
                                                        "signin",
                                                        new String[]{},
                                                        credentials
                                                )
                                        );
                                        commandExecutor.addCommandExecutionToQueue(
                                                commandExecutionSignin
                                        );
                                        context.setUserID(Integer.parseInt(result.getMessage().strip()));
                                    } else {
                                        JOptionPane.showMessageDialog(null, messageBundle.getString("error.incorrectUsername"));
                                    }
                                }
                        );
                        commandExecutor.addCommandExecutionToQueue(
                                commandExecutionGetUserID
                        );
                    }
                );
                credentialsForm.showPopup();
            });
            accountMenu.add(this.signinMenuItem);

            this.signupMenuItem = new JMenuItem();
            this.signupMenuItem.addActionListener(e -> {
                CredentialsForm credentialsForm = new CredentialsForm(context);
                credentialsForm.addSubmitListener(
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
                credentialsForm.showPopup();
            });
            this.accountMenu.add(this.signupMenuItem);
        }

        {
            this.viewMenu = new JMenu();

            this.tableViewMenuItem = new JMenuItem();
            this.tableViewMenuItem.addActionListener(
                    e -> {
                        this.pageChanger.showPage(Pages.PERSON_TABLE_PAGE.name());
                    }
            );
            this.viewMenu.add(this.tableViewMenuItem);

            this.visualViewMenuItem = new JMenuItem();
            this.visualViewMenuItem.addActionListener(
                    e -> {
                        this.pageChanger.showPage(Pages.PERSON_VISUAL_PAGE.name());
                    }
            );
            this.viewMenu.add(this.visualViewMenuItem);
        }

        {
            this.infoMenu = new JMenu();

            this.infoMenuItem = new JMenuItem();
            this.infoMenuItem.addActionListener(e -> {
                var commandExecution = new CommandExecution(
                        new CommandInputInfo(
                                "info"
                        )
                );
                commandExecution.addLitstener(
                        result -> {
                            JOptionPane.showMessageDialog(this.parentFrame, result.getMessage());
                        }
                );
                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(commandExecution);
            });

            this.infoMenu.add(this.infoMenuItem);
        }

        {
            this.settingsMenu = new JMenu();

            this.languageMenuItem = new JMenuItem();
            this.languageMenuItem.addActionListener(e -> {
                var chosenLanguage = this.languageChoicePopup.showAndGetChoice();
                if (chosenLanguage == null) {
                    return;
                }

                if (context.getLocalizationManager().changeLanguage(chosenLanguage)) {
                    this.context.getGlobalRedrawer().redraw();
                }
            });

            this.settingsMenu.add(this.languageMenuItem);
        }

        {
            this.helpMenu = new JMenu();

            this.cliCommandListMenuItem = new JMenuItem();
            this.cliCommandListMenuItem.addActionListener(e -> {
                var commandExecution = new CommandExecution(
                    new CommandInputInfo(
                        "help"
                    )
                );
                commandExecution.addLitstener(
                    result -> {
                        JOptionPane.showMessageDialog(this.parentFrame, result.getMessage());
                    }
                );
                this.context.getCommandQueueExecutor().addCommandExecutionToQueue(commandExecution);
            });

            this.aboutMenuItem = new JMenuItem();
            this.aboutMenuItem.addActionListener(e -> {
                JOptionPane.showMessageDialog(this.parentFrame, "Lab 8");
            });

            this.helpMenu.add(this.cliCommandListMenuItem);
            this.helpMenu.add(this.aboutMenuItem);
        }

        this.usernameItem = new RightStickingLabelMenuItem(this.context.getCredentialsManager().getLogin());
        this.context.getCredentialsManager().addListener(
            credentials -> {
                this.usernameItem.setText(credentials.getLogin());
            }
        );

        this.add(this.accountMenu);
        this.add(this.viewMenu);
        this.add(this.infoMenu);
        this.add(this.settingsMenu);
        this.add(this.helpMenu);
        this.add(usernameItem);

        this.redraw();
    }

    @Override
    public void redraw() {
        var messageBundle = context.getLocalizationManager().getMessageBundle();
        this.accountMenu.setText(messageBundle.getString("gui.menu.account"));
        this.signinMenuItem.setText(messageBundle.getString("gui.menu.item.signin"));
        this.signupMenuItem.setText(messageBundle.getString("gui.menu.item.signup"));

        this.viewMenu.setText(messageBundle.getString("gui.menu.view"));
        this.tableViewMenuItem.setText(messageBundle.getString("gui.menu.item.tableView"));
        this.visualViewMenuItem.setText(messageBundle.getString("gui.menu.item.visualView"));

        this.infoMenu.setText(messageBundle.getString("gui.menu.info"));
        this.infoMenuItem.setText(messageBundle.getString("gui.menu.item.info"));

        this.settingsMenu.setText(messageBundle.getString("gui.menu.settings"));
        this.languageMenuItem.setText(messageBundle.getString("gui.menu.item.language"));

        this.helpMenu.setText(messageBundle.getString("gui.menu.help"));
        this.cliCommandListMenuItem.setText(messageBundle.getString("gui.menu.item.cliCommandList"));
        this.aboutMenuItem.setText(messageBundle.getString("gui.menu.item.about"));
    }

    private static class RightStickingLabelMenuItem extends JMenuItem {
        private JLabel label;
        public RightStickingLabelMenuItem(String label) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Add some spacing
            this.label = new JLabel(label);
            add(this.label, BorderLayout.EAST);
        }

        public void setText(String text) {
            this.label.setText(text);
        }
    }
}
