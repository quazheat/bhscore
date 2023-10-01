package fr.openai.starter;

import fr.openai.reader.LogRNT;
import fr.openai.notify.ClipboardUtil;
import fr.openai.starter.uuid.UuidChecker;
import fr.openai.starter.uuid.UuidProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class LoginManager {
    public static void attemptLogin() {
        UuidChecker uuidChecker = new UuidChecker();
        if (uuidChecker.isAllowed()) {
            LogRNT logRNT = new LogRNT();
            logRNT.starter();
        } else {
            failHandler();
        }
    }

    private static void failHandler() {
        // Получаем UUID компьютера
        UUID systemUUID = UuidProvider.getUUID();

        // Копируем UUID в буфер обмена
        String sysIdStr = systemUUID != null ? systemUUID.toString() : "UUID not found";

        // Создаем текстовое поле для UUID
        JTextField uuid = new JTextField(sysIdStr);
        uuid.setEditable(false); // Запрещаем редактирование
        uuid.setFont(new Font("Arial", Font.PLAIN, 14)); // Устанавливаем шрифт и размер текста
        uuid.setForeground(Color.BLACK); // Устанавливаем цвет текста
        uuid.setMaximumSize(new Dimension(400, 40)); // Ограничиваем максимальную высоту

        // Создаем компоненты JLabel для высоких полей
        JLabel wrongLabel = new JLabel("Wrong UUID:");
        wrongLabel.setForeground(Color.BLACK);
        JLabel supportLabel = new JLabel("Contact support.");
        supportLabel.setForeground(Color.BLACK);

        // Создаем панель для ввода текста с GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Отступы

        // Добавляем компоненты в панель
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(wrongLabel, gbc);

        gbc.gridy = 1;
        panel.add(uuid, gbc);

        gbc.gridy = 2;
        panel.add(supportLabel, gbc);

        // Создаем сообщение для диалогового окна
        Object[] message = {panel};

        // Создаем кнопку "OK" для закрытия окна
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClipboardUtil.copyToClipboard(sysIdStr);
                System.exit(1); // Завершаем работу программы
            }
        });

        // Создаем опции диалогового окна
        Object[] options = {okButton};

        // Отображаем диалоговое окно с сообщением и кнопкой "OK"
        int result = JOptionPane.showOptionDialog(null, message, "Login Failed",
                JOptionPane.NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }
}