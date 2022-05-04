import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class StatisticalAnalyzer {
    public static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ".toUpperCase();
    private static Path inputFilePath;
    private static Map<Character, Integer> charStatMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        inputFilePath = Paths.get("C:\\test\\file1.txt");
        //reads encrypted text from the file to string
        byte[] encryptedFileContent = Files.readAllBytes(Paths.get(inputFilePath.toString()));
        String encryptedText = new String(encryptedFileContent, StandardCharsets.UTF_8);

        //load alphabet to charStatMap for compare next
        for (int i = 0; i < ALPHABET.length(); i++) {
            charStatMap.put(ALPHABET.charAt(i), 0);
        }

        //load statistics for each character of the encrypted text
        for (int i = 0; i < encryptedText.length(); i++) {
            char desiredChar = Character.toUpperCase(encryptedText.charAt(i));
            //if map contains character of encrypted text
            if (charStatMap.containsKey(desiredChar)) {
                //desiredCharValue for increment if char was founded
                int desiredCharVal = charStatMap.get(desiredChar);
                desiredCharVal++;
                charStatMap.put(desiredChar, desiredCharVal);
            }
        }

        SortedSet<Character> keys = new TreeSet<>(charStatMap.keySet());
        int charMaxCount = 0;
        char keyChar = 0;
        for (Character key : keys) {
            int value = charStatMap.get(key);
            if (value > charMaxCount) {
                charMaxCount = value;
                keyChar = key;
            }
        }
        System.out.println(keyChar + " = " + charMaxCount);

        System.out.println(findKey(keyChar, ' '));
        System.out.println(findKey(keyChar, 'А'));
        System.out.println(findKey(keyChar, 'О'));
        System.out.println(findKey(keyChar, 'Е'));
        System.out.println(findKey(keyChar, 'И'));

        for (var entry : charStatMap.entrySet()) {
            System.out.println(entry);
        }
    }

    //correct work of algo to find real key needed
    public static int findKey(char keyChar, char keyRealChar) {
        int cipherKey = 0;
        if (ALPHABET.indexOf(keyRealChar) >= ALPHABET.indexOf(keyChar)) {
            cipherKey = Math.abs(ALPHABET.length() - (ALPHABET.indexOf(keyRealChar) - ALPHABET.indexOf(keyChar)));
        } else {
            cipherKey = Math.abs(ALPHABET.indexOf(keyRealChar) - ALPHABET.indexOf(keyChar));
        }
        return cipherKey;
    }
}
