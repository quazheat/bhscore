package fr.openai.ui.pp;

import fr.openai.b.dzz;
import fr.openai.discordfeatures.de;
import fr.openai.o.OO;
import fr.openai.ui.ny.cui;
import fr.openai.ui.ny.hj;

import javax.swing.*;
import java.awt.*;

public class aez extends JPanel {
    private final OO OO = new OO();
    final dzz dzz = new dzz();

    public aez() {
        setLayout(new BorderLayout());
        String main = dzz.us();

        JSlider uq = new JSlider(JSlider.HORIZONTAL, 10, 510, dzz.bq());
        uq.setMajorTickSpacing(100);
        uq.setMinorTickSpacing(10);
        uq.setPaintTicks(true);
        uq.setPaintLabels(true);
        uq.setSnapToTicks(true);



        JLabel qq = new JLabel(" Скорость обработки: " + dzz.bq() + " ms");
        JButton qqz = new JButton("Онлайн");
        Font customFont = new Font("Arial", Font.PLAIN, 9);
        qqz.setFont(customFont);
        cui.cios(qqz);
        qqz.setFocusPainted(false);

        if (main == null || main.length() <= 3) {
            main = de.gIs();
            if (main == null || main.length() <= 3) {
                qqz.setEnabled(false);
            }
        }

        qqz.addActionListener(e -> OO.a());
        uq.addChangeListener(e -> {
            int value = uq.getValue();
            dzz.qq(value);
            qq.setText(" Скорость обработки после перезапуска: " + value + " ms");
        });

        JTextField te = new JTextField(10);

        te.addActionListener(e -> {
            try {
                int v = Integer.parseInt(te.getText());
                if (v >= 10 && v <= 510) {
                    uq.setValue(v);
                    dzz.qq(v);
                } else {
                    te.setText(Integer.toString(uq.getValue()));
                }
            } catch (NumberFormatException ex) {
                te.setText(Integer.toString(uq.getValue()));
            }
        });

        hj hhg = new hj(
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

        JPanel o = new JPanel(new BorderLayout());
        o.add(uq, BorderLayout.NORTH);
        o.add(qq, BorderLayout.SOUTH);


        JPanel eq = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        qqz.setMinimumSize(new Dimension(69, 30));
        gbc.insets = new Insets(0, 0, 0, 315);
        eq.add(qqz, gbc);


        JPanel ps = new JPanel(new GridLayout(1, 1));
        ps.add(eq);
        add(ps, BorderLayout.NORTH);
        add(o, BorderLayout.CENTER);
        hhg.setFont(customFont);
        add(hhg, BorderLayout.WEST);

    }
}
