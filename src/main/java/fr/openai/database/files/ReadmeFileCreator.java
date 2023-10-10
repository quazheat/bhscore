    package fr.openai.database.files;

    import java.io.FileWriter;
    import java.io.IOException;

    public class ReadmeFileCreator {
        private static final String README_PATH = "README.txt";

        public void createReadmeFile() {
            try {
                FileWriter readmeWriter = new FileWriter(README_PATH);
                String readmeContent = "Привет, читатель благородный!\n";
                readmeContent += "Вас встречает информационный текст,\n";
                readmeContent += "Сначала в хабе загляните, добрый сэр,\n";
                readmeContent += "А затем устремитесь в мир компьютерных фей.\n";
                readmeContent += "\n";
                readmeContent += "Для доступа к приложению волшебному,\n";
                readmeContent += "Скопируйте ID, логина чудного экрана.\n";
                readmeContent += "\n";
                readmeContent += "В файле config.properties строка upFQ = 100,\n";
                readmeContent += "Изменить можно, где 10 - быстродействие высшее,\n";
                readmeContent += "И затраты великие на вашем ПК оно.\n";
                readmeContent += "500 - медлительность, но затраты невелики.\n";
                readmeContent += "\n";
                readmeContent += "Слова в список запрещенных добавить можно,\n";
                readmeContent += "В трее иконка появится там, внизу экрана.\n";
                readmeContent += "Кликните правой, редактор слов откроется вам,\n";
                readmeContent += "Запрещенных слов просто несчислимо.\n";
                readmeContent += "Там можно и белый список ведать немало.\n";
                readmeContent += "\n";
                readmeContent += "Они работают по принципу корневому,\n";
                readmeContent += "«Ху» внесете, нарушение придется вам.\n";
                readmeContent += "Ибо корень ху в слове есть, и даже если нечто плодное,\n";
                readmeContent += "Программа считает - ваша вина оно.\n";
                readmeContent += "\n";
                readmeContent += "Белый список по-другому работает великолепно,\n";
                readmeContent += "Слово идентичное внесете - программа молчит.\n";
                readmeContent += "\n";
                readmeContent += "И слово это больше не нарушит спокойствия ночи.\n";
                readmeContent += "\n";
                readmeContent += "Слова в редакторе слов добавить можно новые,\n";
                readmeContent += "Теперь не через JSON, а в программе прямо.\n";
                readmeContent += "И есть злостный мод, рейдж без предупреждения,\n";
                readmeContent += "Копируются в буфер - нарушение ужасное.\n";
                readmeContent += "\n";
                readmeContent += "Добавлять слова можно в WORDS.json, если умеете,\n";
                readmeContent += "Перезапустите программу - слова в силе будут теперь.\n";
                readmeContent += "\n";
                readmeContent += "А если ошибку в репорте замечаете вы, добрейший мой друг,\n";
                readmeContent += "Попробуйте покликать отправить, текст изменить - так бывает, как сглаз.\n";
                readmeContent += "И не пишите сюда пустые слова и бессмысленный мрак,\n";
                readmeContent += "Айдишник виден мне всегда, не ведитесь на звук.\n";
                readmeContent += "\n";
                readmeContent += "И если отправите много раз одну же ноту,\n";
                readmeContent += "Не страшно, ссылки на скриншоты кидайте в охапку свою.\n";


                readmeWriter.write(readmeContent);
                readmeWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
