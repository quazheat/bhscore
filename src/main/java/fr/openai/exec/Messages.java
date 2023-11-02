package fr.openai.exec;


import fr.openai.filter.Validator;

public class Messages {
    public static String getMessage(String line) {
        if (Validator.isNotValid(line)) {
            return null;
        }

        String message = extract(line);
        return !message.isEmpty() ? message : null;
    }
    public static String getMessageRPC(String line) {

        String message = extract(line);
        return !message.isEmpty() ? message : null;
    }


    private static String extract(String line) {
        int chatIndex = line.indexOf(": [CHAT]");
        if (chatIndex == -1) {
            // The entire line is considered the message if ": [CHAT]" is not found
            return line;
        }

        int start = chatIndex + ": [CHAT]".length(); // Start after the ": [CHAT]"
        int end = line.indexOf("»", chatIndex);

        if (end != -1) {
            start = end + 1;
        } else {
            int colonIndex = line.indexOf(":", chatIndex);
            if (colonIndex != -1) {
                start = colonIndex + 1;
            }
        }

        return line.substring(start).trim();
    }


    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a raw string: ");
        String rawString = scanner.nextLine();

        String message = getMessage(rawString);
        if (message != null) {
            System.out.println("Extracted Message: " + message);
            return;
        }

        System.out.println("No valid message found in the input string.");
    }*/
}
