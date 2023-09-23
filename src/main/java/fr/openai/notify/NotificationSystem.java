package fr.openai.notify;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NotificationSystem {

    private final List<JDialog> notifications = new ArrayList<>();
    private int currentY = 50;
    private int standardY = 50;
    private ImageIcon icon; // Для хранения иконки
    public NotificationSystem() {
        // Загрузка иконки из URL
        try {
            URL iconURL = new URL("https://cdn-icons-png.flaticon.com/128/7505/7505050.png");
            icon = new ImageIcon(iconURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showNotification(String playerName, String violation) {
        // Создаем диалоговое окно уведомления
        JDialog notificationDialog = new JDialog();
        notificationDialog.setIconImage(icon.getImage());
        notificationDialog.setSize(300, 100);
        notificationDialog.setLayout(new BorderLayout());
        notificationDialog.setUndecorated(true); // Запретить перемещение и убрать кнопку крестика
        notificationDialog.setAlwaysOnTop(true);
        notificationDialog.setFocusableWindowState(false);
        notifications.add(notificationDialog);
        notificationDialog.setVisible(true);


        // Позиционируем окно уведомления справа снизу экрана и поднимаем на 20 пикселей
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - notificationDialog.getWidth());
        int y = (int) (screenSize.getHeight() - notificationDialog.getHeight() - currentY);
        notificationDialog.setLocation(x, y);

        // Создаем панель для текстовых полей и кнопок
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout());
        notificationPanel.setBackground(Color.DARK_GRAY); // Установка цвета фона

        // Создаем текстовые поля для имени игрока и нарушения
        JLabel playerNameLabel = new JLabel(playerName, SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Serif", Font.BOLD, 18)); // Установка шрифта для имени игрока
        playerNameLabel.setForeground(Color.BLACK); // Цвет текста имени игрока

        JTextField violationField = new JTextField(violation);
        violationField.setEditable(false); // Нарушение нельзя редактировать
        violationField.setForeground(Color.BLACK);
        violationField.setFont(new Font("Serif", Font.PLAIN,16)); // Установка шрифта для нарушения
        violationField.setBackground(Color.GRAY); // Цвет текста имени игрока

        // Запретить копирование, выделение, редактирование и установку фокуса в текстовом поле нарушения
        violationField.setHighlighter(null); // Запретить выделение текста
        violationField.setTransferHandler(null); // Запретить копирование текста
        violationField.setEditable(false); // Запретить редактирование текста
        violationField.setFocusable(false); // Запретить установку фокуса

        // Создаем панель для кнопок (mute/warn и закрыть)
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Создаем кнопку mute/warn
        JButton actionButton = new JButton("mute\\warn");
        actionButton.setBackground(new Color(29, 29, 29)); // Цвет кнопки
        actionButton.setForeground(Color.WHITE); // Цвет текста кнопки
        actionButton.setFocusPainted(false); // Убрать отрисовку рамки вокруг текста кнопки


        // Создаем кнопку закрытия (красная кнопка с белым текстом "X")
        JButton closeButton = new JButton("X");
        closeButton.setBackground(Color.RED); // Цвет кнопки закрытия
        closeButton.setForeground(Color.WHITE); // Цвет текста кнопки закрытия
        closeButton.setFocusPainted(false); // Убрать фокусную рамку

        // Добавляем обработчик события клика для кнопки закрытия
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                notifications.remove(notificationDialog);
                notificationDialog.dispose();
                currentY -= 20; // Понижаем текущую высоту на 20 пикселей
            }
        });
        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Левый клик
                    String playerName = playerNameLabel.getText();
                    String command = "/mute " + playerName + "  ";
                    copyToClipboard(command);
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    currentY -= 20; // Понижаем текущую высоту на 20 пикселей
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Правый клик
                    String playerName = playerNameLabel.getText();
                    String command = "/warn " + playerName + "  "; // Варн при правом клике
                    copyToClipboard(command);
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    currentY -= 20; // Понижаем текущую высоту на 20 пикселей
                }
            }
        });

        // Добавляем кнопки на панель для кнопок
        buttonPanel.add(actionButton, BorderLayout.CENTER);
        buttonPanel.add(closeButton, BorderLayout.EAST);

        // Добавляем панель с текстовыми полями и кнопками на общую панель уведомления
        notificationPanel.add(playerNameLabel, BorderLayout.NORTH);
        notificationPanel.add(violationField, BorderLayout.CENTER);
        notificationPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Добавляем общую панель уведомления на диалоговое окно
        notificationDialog.add(notificationPanel);

        // Добавляем уведомление в список и показываем его
        notifications.add(notificationDialog);
        notificationDialog.setVisible(true);
        currentY += 20; // Увеличиваем текущую высоту на 20 пикселей для следующего уведомления
    }

    // Метод для копирования текста в буфер обмена
    private static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotificationSystem system = new NotificationSystem();
            system.showNotification("Player1", "Violation1");
            system.showNotification("Player2", "Violation2");
        });
    }
}
