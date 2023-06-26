package client.gui.page;

import javax.swing.*;
import java.awt.*;

public class PageChanger {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final CardLayout multiPageCardLayout;

    public PageChanger(JFrame frame, JPanel mainPanel, CardLayout multiPageCardLayout) {
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.multiPageCardLayout = multiPageCardLayout;
    }

    public void showPage(String pageName) {
        this.multiPageCardLayout.show(this.mainPanel, pageName);
        this.frame.revalidate();
        this.frame.repaint();
    }
}
