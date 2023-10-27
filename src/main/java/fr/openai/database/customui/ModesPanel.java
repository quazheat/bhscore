package fr.openai.database.customui;

import fr.openai.database.ConfigManager;

import javax.swing.*;
import java.awt.*;

public class ModesPanel extends JPanel {

    public ModesPanel() {
        setLayout(new BorderLayout());

        // Create an instance of ConfigManager
        ConfigManager configManager = new ConfigManager();

        JSlider upFQSlider = new JSlider(JSlider.HORIZONTAL, 10, 510, configManager.getUpFQ());
        upFQSlider.setMajorTickSpacing(100); // Adjusted major tick spacing
        upFQSlider.setMinorTickSpacing(10);
        upFQSlider.setPaintTicks(true);
        upFQSlider.setPaintLabels(true);
        upFQSlider.setSnapToTicks(true); // Snap to the nearest tick


        // Create a label to display the current upFQ value
        JLabel upFQLabel = new JLabel("Скорость обработки: " + configManager.getUpFQ() + " ms");

        upFQSlider.addChangeListener(e -> {
            int value = upFQSlider.getValue();
            configManager.setUpFQ(value); // Use the instance to call non-static method
            upFQLabel.setText("Скорость обработки после перезапуска: " + value + " ms"); // Update the label with the new value
        });

        // Add a JTextField for user input
        JTextField upFQTextField = new JTextField(10); // 10 is the initial text field width

        // Add an ActionListener to update the slider when text is entered
        upFQTextField.addActionListener(e -> {
            try {
                int value = Integer.parseInt(upFQTextField.getText());
                if (value >= 10 && value <= 510) {
                    upFQSlider.setValue(value);
                    configManager.setUpFQ(value);
                } else {
                    upFQTextField.setText(Integer.toString(upFQSlider.getValue()));
                }
            } catch (NumberFormatException ex) {
                upFQTextField.setText(Integer.toString(upFQSlider.getValue()));
            }
        });

        // Disclaimer about changes requiring a reset
        JLabel disclaimerLabel = new JLabel("Слова добавляются моментально, для скорости только ребут");

        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(upFQSlider, BorderLayout.NORTH);
        sliderPanel.add(upFQLabel, BorderLayout.SOUTH);

        JPanel textFieldPanel = new JPanel(new BorderLayout());
        textFieldPanel.add(upFQTextField, BorderLayout.NORTH);

        JPanel radioButtonPanel = new JPanel(new GridLayout(2, 1));
        add(radioButtonPanel, BorderLayout.NORTH);
        add(sliderPanel, BorderLayout.CENTER);
        add(textFieldPanel, BorderLayout.SOUTH);
        add(disclaimerLabel, BorderLayout.SOUTH);
    }
}
