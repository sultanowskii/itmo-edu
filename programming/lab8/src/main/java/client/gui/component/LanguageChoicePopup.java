package client.gui.component;

import client.runtime.ClientContext;
import lib.lang.Language;

import javax.swing.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LanguageChoicePopup implements Redrawable {
    private ClientContext context;
    private JPanel panel;
    private JLabel selectLanguageLabel;
    private JComboBox<String> languageChoice;

    public LanguageChoicePopup(ClientContext context) {
        this.context = context;

        var languageOptions = Arrays.stream(Language.values()).map(Language::getHumanName).toArray(String[]::new);

        this.languageChoice = new JComboBox<>(languageOptions);
        this.selectLanguageLabel = new JLabel();

        this.panel = new JPanel();
        this.panel.add(this.selectLanguageLabel);
        this.panel.add(this.languageChoice);
    }

    public Language showAndGetChoice() {
        int result = JOptionPane.showOptionDialog(
            null,
            panel,
            "Language Selection",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        var selectedLanguageHumanName = (String)this.languageChoice.getSelectedItem();
        return Language.getByHumanName(selectedLanguageHumanName);
    }

    @Override
    public void redraw() {
        var messageBundle = context.getLocalizationManager().getMessageBundle();
        this.selectLanguageLabel.setText(messageBundle.getString("gui.popup.selectLanguage"));
    }
}
