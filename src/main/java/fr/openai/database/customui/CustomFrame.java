package fr.openai.database.customui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomFrame extends JFrame {
    private final JTextArea textArea; //

    public CustomFrame(String title, String iconPath, ActionListener submitActionListener) {
        setTitle(title);
        setIconImage(new ImageIcon(iconPath).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.darkGray);

        JLabel label = new JLabel("Текст для отправки:");
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.NORTH);

        textArea = new JTextArea(5, 20); //
        textArea.setBackground(Color.lightGray);
        textArea.setForeground(Color.black);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        CustomButtonUI customButtonUI = new CustomButtonUI();
        JButton submitButton = new JButton("Отправить тикет");
        submitButton.setUI(customButtonUI);
        panel.add(submitButton, BorderLayout.SOUTH);
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(submitActionListener);

        getContentPane().add(panel);
    }

    //
    public JTextArea getTextArea() {
        return textArea;
    }
}
