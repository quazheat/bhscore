package fr.openai.exec;

import fr.openai.handler.filter.Filtering;

public class Executor {
    public void execute(String line, Names names) {
        if (Validator.isValid(line)) {
            return;
        } // Строки с определенными текстами игнорируются

        String message = Messages.getMessage(line);

        // Создаем экземпляр класса Filtering и вызываем onFilter перед выводом "Player Message"
        Filtering filtering = new Filtering();
        filtering.onFilter(names.getFinalName(line), message); // Передаем имя из finalName

        assert message != null;
        if (!message.isEmpty()) {
            System.out.println("Player Message: " + message);

            // Получаем значение finalName с помощью метода getFinalName() из экземпляра Names
            //String finalName = names.getFinalName(line);

            // Выводим finalName для отладки
            //System.out.println("DEBUG Exec: " + finalName);
        }
    }


    public static void executedLog(String line) {
        // Этот метод можно вызывать из других классов для обработки строки
        // Например, Names.executedLog(line);
        System.out.println(line);
    }
}
