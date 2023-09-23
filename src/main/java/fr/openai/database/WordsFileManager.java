package fr.openai.database;

import java.util.Arrays;
import java.util.List;

public class WordsFileManager {
    private static final String WORDS_JSON_PATH = "words.json";
    private final List<String> defWords = Arrays.asList(
            "nigg", "бля", "пизд", "хуй", "крым", "ауе", "gblj", "fuc", "pido", "pida", "cerf", "долбае", "pizd",
            "соси", "sosi", "Sos",
            "сук", "сука", "пидо", "пидр", "суч", "ёб", "хул", "хули", "хуи", "клоун", "бездар", "конч", "героям",
            "слава", "деб", "kz",
            "акбар", "даун", "аут", "хуй", "чмо", "хохл", "хохол", "москаль", "чмо", "хер", "бомж", "лох", "чурк",
            "чурка", "пизд",
            "порн", "порнеб", "sasi", "пизд", "еб", "ebu", "ebe", "eba", "eb", "хуй", "хуе", "даун", "еблан", "негр",
            "ниг", "хуи","нигг","охуе","huy","xuy","деби","долбаё",
            "шлюх", "шалав", "тупой", "аху", "оху", "оху", "хуя", "хуя", "пиздо", "pizd", "ганд", "ганд", "ганд",
            "гей", "пид", "хуе", "негр","новое_слово_без_запятой_в_конце"
    );


    private final FileManager fileManager = new FileManager();
    private final JsonManager jsonManager = new JsonManager();

    public void createWordsFile() {
        if (fileManager.isFileEmpty()) {
            fileManager.deleteFile();
        }

        if (!fileManager.isExist()) {
            jsonManager.createDefaultJsonFile(WORDS_JSON_PATH, defWords);
        }

        if (fileManager.isExist()) {
            jsonManager.parseJsonFile(WORDS_JSON_PATH);
        }
    }
}
