package fr.openai.database.customui;

import fr.openai.database.ConfigManager;

import javax.swing.*;
import java.awt.*;

public class ModesPanel extends JPanel {

    public ModesPanel() {
        setLayout(new BorderLayout());

        // Create an instance of ConfigManager
        ConfigManager configManager = new ConfigManager();

        JSlider upFQSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, configManager.getUpFQ());
        upFQSlider.setMajorTickSpacing(500);
        upFQSlider.setMinorTickSpacing(10);
        upFQSlider.setPaintTicks(true);
        upFQSlider.setPaintLabels(true);

        // Create a label to display the current upFQ value
        JLabel upFQLabel = new JLabel("upFQ: " + configManager.getUpFQ());

        upFQSlider.addChangeListener(e -> {
            int value = upFQSlider.getValue();
            configManager.setUpFQ(value); // Use the instance to call non-static method
            upFQLabel.setText("upFQ: " + value); // Update the label with the new value
        });

        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(upFQSlider, BorderLayout.NORTH);
        sliderPanel.add(upFQLabel, BorderLayout.SOUTH);

        JPanel radioButtonPanel = new JPanel(new GridLayout(2, 1));
        add(radioButtonPanel, BorderLayout.NORTH);
        add(sliderPanel, BorderLayout.CENTER);
    }
}
