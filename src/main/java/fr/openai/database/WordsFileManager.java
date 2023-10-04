package fr.openai.database;

import fr.openai.database.files.FileManager;

import java.util.Arrays;
import java.util.List;

public class WordsFileManager {
    private static final String WORDS_JSON_PATH = "words.json";
    private final List<String> defWords = Arrays.asList(
            "nigg", "бля", "пизд", "хуй", "крым", "ауе", "gblj", "fuc", "pido", "pida", "cerf", "долбае", "pizd",
            "соси", "sosi","ahue","ahui","мать тво","отчим","твоя мать",
            "сук", "сука", "пидо", "пидр", "суч", "ёб", "хул", "хули", "хуи", "клоун", "бездар", "героям",
            "слава", "деб","ебу","выеб","заеб","аеба","аеби",
            "акбар", "даун", "аут", "хуй", "чмо", "хохл", "хохол", "москаль", "чмо", "хер", "бомж", "чурк",
            "чурка", "пизд","нигер","нигр",
            "порн", "порнеб", "пизд", "ебло", "ebu", "ebe", "eba", "ebl","ebn", "хуй", "хуе", "даун", "еблан", "негр",
            "ниге", "хуи","нигг","охуе","huy","xuy","деби","долбаё",
            "шлюх", "шалав", "тупой", "аху", "оху", "оху", "хуя", "хуя", "пиздо", "pizd", "ганд", "ганд", "ганд",
            "гей", "пид", "хуе", "негр", "ебал", "ебли"
    );
    private final List<String> defWwlist = Arrays.asList(
            "оскорблять", "чмок","чебурашка","хлеб","хлебушек","чебурашк","чебурашки",
            "хлебу","hlebyshek","расслабляем", "расслабляемся", "расслабляет","расслабляешь","расслабляетесь","расслабляйся","употребляешь",
            "употреблять","небуду","употребляет"
    );


    private final FileManager fileManager = new FileManager();
    private final JsonManager jsonManager = new JsonManager();

    public void createWordsFile() {
        if (fileManager.isFileEmpty()) {
            fileManager.deleteFile();
        }

        if (!fileManager.isExist()) {
            jsonManager.createDefaultJsonFile(WORDS_JSON_PATH, defWords, defWwlist);
        } else {
            jsonManager.parseJsonFile(WORDS_JSON_PATH);
        }
    }
}