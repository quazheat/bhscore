package fr.openai.database.editor;

import fr.openai.database.*;
import fr.openai.database.customui.CustomButtonUI;
import fr.openai.database.customui.CustomTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Editor {
    private final AddNewWord addNewWord = new AddNewWord(this);
    private final RemoveWord removeWord = new RemoveWord(this);

    private final AddNewWhitelistWord addNewWhitelistWord = new AddNewWhitelistWord(this);
    private final RemoveWhitelistWord removeWhitelistWord = new RemoveWhitelistWord(this);
    private final JTextField inputTextField = new JTextField();

    private final JFrame frame; // Добавьте поле для хранения окна
    private final JLabel outputLabel; // Добавьте поле для вывода сообщений как лейбл

    public Editor() {

        frame = new JFrame("Word Editor");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Закрывать только окно Editor, не завершать приложение
        frame.setSize(400, 160);

        JTabbedPane tabbedPane = new JTabbedPane();
        JTextField forbiddenInputTextField = new JTextField();
        JPanel forbiddenPanel = createPanel("Ругательства", forbiddenInputTextField);
        JTextField whitelistInputTextField = new JTextField();
        JPanel whitelistPanel = createPanel("Белый список", whitelistInputTextField);

        tabbedPane.addTab("Ругательства", forbiddenPanel);
        tabbedPane.addTab("Белый список", whitelistPanel);

        // Установка пользовательского UI для вкладок
        tabbedPane.setUI(new CustomTab());

        frame.getContentPane().add(tabbedPane);

        // Инициализация JLabel для вывода сообщений
        outputLabel = new JLabel();
        outputLabel.setHorizontalAlignment(JLabel.CENTER); // Выравнивание по центру
        outputLabel.setForeground(Color.BLACK); // Цвет текста
        frame.add(outputLabel, BorderLayout.SOUTH); // Добавить JLabel в нижней части окна

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private JPanel createPanel(String tabTitle, JTextField inputTextField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.lightGray);

        JPanel inputButtonPanel = new JPanel();
        inputButtonPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel inputLabel = new JLabel("Введите слово:");
        inputLabel.setForeground(Color.black); // Цвет текста
        inputPanel.setBackground(Color.LIGHT_GRAY); // Устанавливаем фон для inputPanel в серый цвет

        inputTextField.setColumns(20);
        inputPanel.add(inputLabel);
        inputPanel.add(inputTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Добавить");
        JButton removeButton = new JButton("Удалить");

        CustomButtonUI.setCustomStyle(addButton);
        CustomButtonUI.setCustomStyle(removeButton);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.setBackground(Color.LIGHT_GRAY); // Устанавливаем фон для buttonPanel в серый цвет


        inputButtonPanel.add(inputPanel, BorderLayout.CENTER);
        inputButtonPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(inputButtonPanel, BorderLayout.NORTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newWord = inputTextField.getText();
                if (!newWord.isEmpty()) {
                    if (tabTitle.equals("Ругательства")) {
                        addNewWord.addNewWord(newWord);
                    } else if (tabTitle.equals("Белый список")) {
                        addNewWhitelistWord.addNewWhitelistWord(newWord);
                    }
                }
                inputTextField.setText("");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String wordToRemove = inputTextField.getText();
                if (!wordToRemove.isEmpty()) {
                    if (tabTitle.equals("Ругательства")) {
                        removeWord.removeWord(wordToRemove);
                    } else if (tabTitle.equals("Белый список")) {
                        removeWhitelistWord.removeWhitelistWord(wordToRemove);
                    }
                }
                inputTextField.setText("");
            }
        });

        return panel;
    }


    public void setOutputText(String message) {
        // Устанавливаем фон и цвет текста для outputLabel
        outputLabel.setText(message);
        outputLabel.setForeground(Color.BLACK); // Цвет текста внутри outputLabel

        // Если вы хотите изменить фон и цвет текста для других элементов, таких как кнопки или текстовые поля,
        // вы можете делать это аналогичным образом, как показано ниже:


        // И так далее для других элементов, которые вы хотите изменить
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Editor();
            }
        });
    }
}
