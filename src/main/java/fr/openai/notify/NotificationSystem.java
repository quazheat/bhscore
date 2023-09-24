package fr.openai.notify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import fr.openai.exec.NameFix;


public class NotificationSystem {

    private final List<JDialog> notifications = new ArrayList<>();
    private int currentY = 50;
    private int standardY = 50;

    public void showTestNotification() {
        SwingUtilities.invokeLater(() -> {
            JDialog notificationDialog = new JDialog();
            notificationDialog.setLayout(new BorderLayout());
            notificationDialog.setUndecorated(true);
            notificationDialog.setAlwaysOnTop(true);
            notificationDialog.setFocusableWindowState(false);

            // Устанавливаем размер окна и располагаем его по центру экрана
            int width = 300;
            int height = 100;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((screenSize.getWidth() - width) / 2);
            int y = (int) ((screenSize.getHeight() - height) / 2);
            notificationDialog.setBounds(x, y, width, height);

            // Создаем кнопку для закрытия окна
            JButton closeButton = new JButton("X");
            closeButton.setBackground(Color.RED);
            closeButton.setForeground(Color.WHITE);
            closeButton.setFocusPainted(false);

            // Добавляем обработчик события клика для кнопки закрытия
            closeButton.addActionListener(e -> {
                notifications.remove(notificationDialog);
                notificationDialog.dispose();
            });

            // Создаем панель для текстовых полей и кнопок
            JPanel notificationPanel = new JPanel();
            notificationPanel.setLayout(new BorderLayout());
            notificationPanel.setBackground(Color.DARK_GRAY);

            // Создаем текстовое поле для надписи "logged in!"
            JLabel loginLabel = new JLabel("logged in!");
            loginLabel.setFont(new Font("Serif", Font.BOLD, 18));
            loginLabel.setForeground(Color.WHITE);
            loginLabel.setHorizontalAlignment(SwingConstants.CENTER); // Выравнивание по центру

            // Добавляем текстовое поле и кнопку на панель
            notificationPanel.add(loginLabel, BorderLayout.CENTER);
            notificationPanel.add(closeButton, BorderLayout.EAST);

            // Добавляем панель на диалоговое окно
            notificationDialog.add(notificationPanel);

            notifications.add(notificationDialog);
            notificationDialog.setVisible(true);
        });
    }

    public void showNotification(String playerName, String violation) {
        // Создаем диалоговое окно уведомления
        JDialog notificationDialog = new JDialog();
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



        // Создаем кнопку закрытия (красная кнопка с белым текстом "X")
        JButton closeButton = new JButton("X");
        closeButton.setBackground(Color.RED); // Цвет кнопки закрытия
        closeButton.setForeground(Color.WHITE); // Цвет текста кнопки закрытия
        closeButton.setFocusPainted(false); // Убрать фокусную рамку


        // Добавляем обработчик события клика для кнопки закрытия
        // Добавляем обработчик события клика для кнопки закрытия
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (notifications.contains(notificationDialog)) { // Проверяем, что окно все еще находится в списке уведомлений
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    currentY -= 20; // Понижаем текущую высоту на 20 пикселей
                }
            }
        });

        // Создаем кнопку mute/warn skyblock
        JButton skyblockButton = new JButton("mute\\warn");
        skyblockButton.setBackground(new Color(29, 29, 29)); // Цвет кнопки
        skyblockButton.setForeground(Color.WHITE); // Цвет текста кнопки
        skyblockButton.setFocusPainted(false); // Убрать отрисовку рамки вокруг текста кнопки
        // Добавляем обработчик события клика для кнопки mute/warn skyblock
        skyblockButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Левый клик
                    String playerName = NameFix.sbFix(playerNameLabel.getText()); // Используем NameFix для обработки имени
                    String command = "/mute " + playerName + "  ";
                    ClipboardUtil.copyToClipboard(command);
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    currentY -= 20; // Понижаем текущую высоту на 20 пикселей
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Правый клик
                    String playerName = NameFix.sbFix(playerNameLabel.getText()); // Используем NameFix для обработки имени
                    String command = "/warn " + playerName + "  "; // Варн при правом клике
                    ClipboardUtil.copyToClipboard(command);
                    notifications.remove(notificationDialog);
                    notificationDialog.dispose();
                    currentY -= 20; // Понижаем текущую высоту на 20 пикселей
                }
            }
        });

        // Добавляем кнопки на панель для кнопок
        buttonPanel.add(skyblockButton, BorderLayout.CENTER); // Добавляем новую кнопку над mute/warn
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotificationSystem system = new NotificationSystem();
            system.showNotification("Player1", "Violation1");
            system.showNotification("Player2", "Violation2");
        });
    }
}
