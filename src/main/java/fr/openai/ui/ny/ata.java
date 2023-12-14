package fr.openai.ui.ny;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ata extends BasicTabbedPaneUI {

private static final Color a = new Color(0xB0CE5F);
private static final Color b = Color.BLACK;
private static final Color e = Color.DARK_GRAY;
private static final Color o = Color.WHITE;

@Override
protected void paintTabBackground(Graphics g, int var, int tabIndex, int x, int y, int w, int h, boolean var1) {
    Color tabColor = var1 ? a : e;
    g.setColor(tabColor);
    g.fillRect(x, y, w, h);
}

@Override
protected void paintText(Graphics g, int var, Font font, FontMetrics metrics, int i, String t, Rectangle oc, boolean v1) {
    Color textColor = v1 ? b : o;
    g.setColor(textColor);
    g.setFont(font);
    g.drawString(t, oc.x, oc.y + metrics.getAscent());
}
}
