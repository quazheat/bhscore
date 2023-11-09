package fr.openai.ui.panels;

import fr.openai.database.ConfigManager;
import fr.openai.discordfeatures.DiscordRPCDiag;
import fr.openai.exec.Names;
import fr.openai.online.OnlineUserLoader;
import fr.openai.ui.customui.CustomButtonUI;
import fr.openai.ui.customui.CustomHelp;

import javax.swing.*;
import java.awt.*;

public class ModesPanel extends JPanel {
    private final JCheckBox skyBlockCheckBox;
    private final OnlineUserLoader onlineUserLoader = new OnlineUserLoader();
    final ConfigManager configManager = new ConfigManager();

    public ModesPanel() {

        setLayout(new BorderLayout());

        skyBlockCheckBox = new JCheckBox("Скайблок", false); // Initially disabled
        skyBlockCheckBox.setFocusPainted(false);
        skyBlockCheckBox.addActionListener(e -> {
            boolean isEnabled = skyBlockCheckBox.isSelected();
            Names.isSkyBlockEnabled(isEnabled); // Update the RPC state based on the checkbox
        });
        String username = configManager.getUsername();

        JSlider upFQSlider = new JSlider(JSlider.HORIZONTAL, 10, 510, configManager.getUpFQ());
        upFQSlider.setMajorTickSpacing(100); // Adjusted major tick spacing
        upFQSlider.setMinorTickSpacing(10);
        upFQSlider.setPaintTicks(true);
        upFQSlider.setPaintLabels(true);
        upFQSlider.setSnapToTicks(true); // Snap to the nearest tick

        JLabel upFQLabel = new JLabel(" Скорость обработки: " + configManager.getUpFQ() + " ms");
        JButton onlineUsersButton = new JButton("Онлайн");
        Font customFont = new Font("Arial", Font.PLAIN, 9);
        onlineUsersButton.setFont(customFont);
        CustomButtonUI.setCustomStyle(onlineUsersButton);
        onlineUsersButton.setFocusPainted(false);

        if (username == null || username.length() <= 3) {
            username = DiscordRPCDiag.getUsername();
            if (username == null || username.length() <= 3) {
                onlineUsersButton.setEnabled(false);
            }
        }
        onlineUsersButton.addActionListener(e -> onlineUserLoader.loadOnlineUsers());
        upFQSlider.addChangeListener(e -> {
            int value = upFQSlider.getValue();
            configManager.setUpFQ(value);
            upFQLabel.setText(" Скорость обработки после перезапуска: " + value + " ms");
        });

        JTextField upFQTextField = new JTextField(10);

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

        CustomHelp helpButton = new CustomHelp(
                """
                        Ползунок в этой вкладке влияет на две вещи:
                        1) Скорость авто-наказаний — меняется СРАЗУ ЖЕ после изменений.
                        Авто-наказание не работает на скорости 10-20 мс.
                        2) Скорость работы программы — меняется ТОЛЬКО
                        после перезапуска программы.
                                                                                                
                                                                                                xxx
                                                                                                
                        Вы можете менять моды работы программы, их всего три:
                        1) Обычный мод, включен сразу. Кнопка копирует команду в буфер;
                        2) RAGE. Автоматически вставит в чат команду на мут с причиной;
                        3) LOYAL. Автоматически вставит в чат команду на варн с причиной;
                                                                                                
                                                                                                xxx
                                                                                                
                        Слова во вкладке "Words". Добавлять нужно целое слово,
                        будет реагировать на похожие слова. Зачастую, добавлять
                        однокоренные слова НЕ НУЖНО. Если не реагирует, кидайте репорт.
                                                                                                
                                                                                                xxx
                                                                                                
                        Слова, в словаре "Whitelist" и похожие слова,
                        не видны для модуля ругательств.
                        Исключения можно добавлять через запятую, за раз хоть 100 штук.
                                                                                                
                                                                                                xxx
                                                                                                
                        Вкладка "Reports" создана для отправки репорта или предложения
                        по работе этой программы, можно кидать что-то ссылкой и всё такое.
                                                                                                
                                                                                                xxx
                                                                                                
                        Когда вы отправляете репорт или редактируете онлайн-словари,
                        собирается информация:
                        1) Ваш ключ доступа к програме;
                        2) Ваш IP адрес.
                                                
                        Эта информация нужна для того, чтобы быстро забрать доступ.
                        Используя вкладки словарей и отчетов,
                        Вы соглашаетесь на сбор информации для этой цели.
                        """);

        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(upFQSlider, BorderLayout.NORTH);
        sliderPanel.add(upFQLabel, BorderLayout.SOUTH);


        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        onlineUsersButton.setMinimumSize(new Dimension(69, 30));
        gbc.insets = new Insets(0, 16, 0, 0);
        rightPanel.add(onlineUsersButton, gbc);
        gbc.insets = new Insets(0, 0, 0, 250);
        rightPanel.add(skyBlockCheckBox, gbc);


        JPanel radioButtonPanel = new JPanel(new GridLayout(1, 1));
        radioButtonPanel.add(rightPanel); // Add the RPC toggle checkbox to the right corner
        add(radioButtonPanel, BorderLayout.NORTH);
        add(sliderPanel, BorderLayout.CENTER);
        helpButton.setFont(customFont);
        add(helpButton, BorderLayout.WEST);
    }
}
