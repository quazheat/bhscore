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


        CustomHelp helpButton = new CustomHelp(
                """
                        Скорость в этой вкладке влияет на две вещи:
                        1) Скорость авто-наказаний — меняется СРАЗУ ЖЕ после изменений.
                        Авто-наказание не работает на скорости 10-20 мс.
                        2) Скорость работы программы — меняется ТОЛЬКО
                        после перезапуска программы.
                                                                                                
                                                                                                xxx
                                                                                                
                        Вы можете менять моды работы программы, их всего три:
                        1) Обычный мод, включен по стандарту. Вы выбираете наказание сами;
                        2) RAGE. Автоматически вставит в чат команду на мут с причиной;
                        3) LOYAL. Автоматически вставит в чат команду на варн с причиной;
                                                                                                
                                                                                                xxx
                                                                                                
                        Слова во вкладке "Ругательства". Добавлять нужно целое слово,
                        будет реагировать на похожие слова. Зачастую, добавлять
                        однокоренные слова НЕ НУЖНО. Если не реагирует, кидайте репорт.
                                                                                                
                                                                                                xxx
                                                                                                
                        Слов, в словаре "Белый список" и похожие слова,
                        не видны для модуля ругательств.
                        Исключения можно добавлять через запятую, за раз хоть 100 штук.
                                                                                                
                                                                                                xxx
                                                                                                
                        Вкладка "Отчеты" создана для отправки репорта или предложения
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


        JPanel radioButtonPanel = new JPanel(new GridLayout(1, 1));
        add(radioButtonPanel, BorderLayout.NORTH);
        add(sliderPanel, BorderLayout.CENTER);
        add(helpButton, BorderLayout.WEST);
    }
}
