package client.gui;

import client.gui.component.GeneralMenu;
import client.gui.component.Redrawable;
import client.gui.page.*;
import client.gui.util.GlobalRedrawer;
import client.gui.worker.CollectionUpdateWorker;
import client.gui.worker.CommandExecutorWorker;
import client.runtime.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GUI implements Redrawable {
    private final ClientContext context;
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout multiPageCardLayout;
    private final GeneralMenu generalMenu;
    private final PageChanger pageChanger;
    private final Map<String, Page> pageMap;

    public GUI(ClientContext context) {
        this.context = context;

        this.setupWorkers();

        Font defaultFont = new JLabel().getFont().deriveFont(~Font.BOLD);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("Menu.font", defaultFont);
        UIManager.put("MenuItem.font", defaultFont);

        this.frame = new JFrame("Lab8");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1400, 800);

        this.multiPageCardLayout = new CardLayout();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(this.multiPageCardLayout);

        this.frame.add(mainPanel);

        GlobalRedrawer globalRedrawer = new GlobalRedrawer(this);
        this.context.setGlobalRedrawer(globalRedrawer);

        this.pageChanger = new PageChanger(this.frame, this.mainPanel, this.multiPageCardLayout);

        this.generalMenu = new GeneralMenu(this.frame, this.pageChanger, this.context);
        this.frame.setJMenuBar(this.generalMenu);

        {
            this.pageMap = new HashMap<>();
            this.pageMap.put(Pages.TEST_PAGE.name(), new TestPage(this.context, pageChanger));
            this.pageMap.put(Pages.PERSON_TABLE_PAGE.name(), new PersonTablePage(this.context));
            this.pageMap.put(Pages.PERSON_VISUAL_PAGE.name(), new PersonVisualPage(this.context));

            for (var pageName : pageMap.keySet()) {
                this.mainPanel.add(pageName, this.pageMap.get(pageName));
            }
        }

        this.pageChanger.showPage(Pages.PERSON_TABLE_PAGE.name());

        this.frame.setVisible(true);
    }

    private void setupWorkers() {
        SwingWorker<Void, String> commandExecutorWorker = new CommandExecutorWorker(context.getCommandQueueExecutor());
        SwingWorker<Void, Void> collectionUpdateWorker = new CollectionUpdateWorker(context.getCommandQueueExecutor());

        commandExecutorWorker.execute();
        collectionUpdateWorker.execute();
    }

    @Override
    public void redraw() {
        for (var page : this.pageMap.values()) {
            page.redraw();
        }

        this.generalMenu.redraw();
    }
}
