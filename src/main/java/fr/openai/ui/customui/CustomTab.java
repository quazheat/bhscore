package fr.openai.ui.customui;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class CustomTab extends BasicTabbedPaneUI {

private static final Color SELECTED_TAB_COLOR = new Color(0xB0CE5F);
private static final Color SELECTED_TEXT_COLOR = Color.BLACK;
private static final Color UNSELECTED_TAB_COLOR = Color.DARK_GRAY;
private static final Color UNSELECTED_TEXT_COLOR = Color.WHITE;

public CustomTab() {
    super();
}

@Override
protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    Color tabColor = isSelected ? SELECTED_TAB_COLOR : UNSELECTED_TAB_COLOR;
    g.setColor(tabColor);
    g.fillRect(x, y, w, h);
}

@Override
protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
    Color textColor = isSelected ? SELECTED_TEXT_COLOR : UNSELECTED_TEXT_COLOR;
    g.setColor(textColor);
    g.setFont(font);
    g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
}
}
