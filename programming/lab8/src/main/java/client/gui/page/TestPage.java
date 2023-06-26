package client.gui.page;

import client.gui.component.PersonFormPopup;
import client.runtime.ClientContext;

import javax.swing.*;
import java.awt.*;

public class TestPage extends Page {
    private ClientContext context;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private boolean even;
    private final PageChanger pageChanger;

    public TestPage(ClientContext context, PageChanger pageChanger) {
        this.setLayout(new BorderLayout());

        this.context = context;

        this.even = false;
        this.pageChanger = pageChanger;

        this.button1 = new JButton();
        this.button1.addActionListener(
            action -> {
                if (even) {
                    this.redraw();
                } else {
                    this.redraw2();
                }
                even = !even;
            }
        );
        this.button2 = new JButton();
        this.button3 = new JButton();
        this.button3.addActionListener(
            action -> this.pageChanger.showPage(Pages.PERSON_TABLE_PAGE.name())
        );

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        this.redraw();

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void redraw() {
        var messageBundle = this.context.getLocalizationManager().getMessageBundle();
        this.button1.setText(messageBundle.getString("gui.test.button1"));
        this.button2.setText(messageBundle.getString("gui.test.button2"));
        this.button3.setText(messageBundle.getString("gui.test.button3"));
    }

    public void redraw2() {
        this.button1.setText("click");
        this.button2.setText("on the ");
        this.button3.setText("first button!");
    }
}
