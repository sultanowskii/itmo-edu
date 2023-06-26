package client.gui.util;

import client.gui.component.Redrawable;

public class GlobalRedrawer {
    private final Redrawable mainRedrawable;

    public GlobalRedrawer(Redrawable mainRedrawable) {
        this.mainRedrawable = mainRedrawable;
    }

    public void redraw() {
        this.mainRedrawable.redraw();
    }
}
