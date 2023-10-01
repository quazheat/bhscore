package fr.openai.database.files;

import java.io.FileWriter;
import java.io.IOException;

public class ReadmeFileCreator {
    private static final String README_PATH = "README.txt";

    public void createReadmeFile() {
        try {
            FileWriter readmeWriter = new FileWriter(README_PATH);
            String readmeContent = "Привет!\n";
            readmeContent += "Это файл с начальной информацией.\n";
            readmeContent += " \n";
            readmeContent += "СНАЧАЛА НУЖНО ХОТЯ БЫ ЗАЙТИ В ХАБ\n";
            readmeContent += "ТОЛЬКО ПОТОМ ПРОГРАММУ \n";
            readmeContent += " \n";
            readmeContent += "Чтобы получить доступ к приложению, нужно скопировать ID компьютера в экране с ошибкой логина.\n";
            readmeContent += " \n";
            readmeContent += "В файле config.properties есть переменная upFQ = 100,\n";
            readmeContent += "Можете менять ее в пределах 10-500, где 10 = Наивысшее быстродействие, Наивысшие затраты ресурсов ПК.\n";
            readmeContent += "500 = Самая едленная работа программы, Самые низкие затраты ресурсов ПК.\n";
            readmeContent += " \n";
            readmeContent += "Вы можете добавлять новые слова в список запрещенных:\n";
            readmeContent += "Внизу экрана (там где время) появится иконка приложения (в трей),\n";
            readmeContent += "Нажав ПКМ по ней, вы можете открыть редактор слов.\n";
            readmeContent += "Помните, что запрещенные слова работают по принципу однокоренных, а именно:\n";
            readmeContent += "Если вы внесете в список запрещенных слов «ху», то программа будет считать «ХУтава» нарушением,\n";
            readmeContent += "Потому что там есть корень ху, который Вы добавили.\n";
            readmeContent += " \n";
            readmeContent += "Список whitelist (Белый список) не действует по такому принципу, вы должны добавить идентичное слово,\n";
            readmeContent += "чтобы программа перестала на него реагировать, например: \n";
            readmeContent += "вы внесете в список запрещенных слов «ху», , но «хутава» в белый список,\n";
            readmeContent += "теперь это слово 100% не нарушение, но как только в чате напишут «хуб, хубабуба», это будет считаться нарушением.\n";
            readmeContent += " \n";
            readmeContent += "Вы можете добавлять слова напрямую в файле WORDS.json, если умеете. \n";
            readmeContent += " \n";
            readmeContent += "Чтобы слова вступили в силу, требуется перезапуск программы (НЕ ИГРЫ). \n";
            readmeContent += " \n";
            readmeWriter.write(readmeContent);
            readmeWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
